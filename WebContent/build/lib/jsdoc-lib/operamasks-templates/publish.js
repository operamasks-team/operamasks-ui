/** Called automatically by JsDoc Toolkit. */
function publish(symbolSet) {
	publish.conf = {  // trailing slash expected for dirs
		ext:         ".html",
		outDir:      JSDOC.opt.d || SYS.pwd+"../out/jsdoc/",
		templatesDir: JSDOC.opt.t || SYS.pwd+"../templates/jsdoc/",
		symbolsDir:  "symbols/",
		srcDir:      "symbols/src/",
		cssDir:      "css/",
		imagesDir:   "css/images/",
		tabImagesDir:"css/images/tabs/",
		jsDir:       "js/"
	};
	
	// is source output is suppressed, just display the links to the source file
	if (JSDOC.opt.s && defined(Link) && Link.prototype._makeSrcLink) {
		Link.prototype._makeSrcLink = function(srcFilePath) {
			return "&lt;"+srcFilePath+"&gt;";
		}
	}
	
	// create the folders and subfolders to hold the output
	IO.mkPath((publish.conf.outDir+"symbols/src").split("/"));
	IO.mkPath((publish.conf.outDir+publish.conf.cssDir));
	IO.mkPath((publish.conf.outDir+publish.conf.imagesDir));
	IO.mkPath((publish.conf.outDir+publish.conf.tabImagesDir));
	IO.mkPath((publish.conf.outDir+publish.conf.jsDir));
		
	// used to allow Link to check the details of things being linked to
	Link.symbolSet = symbolSet;

	// create the required templates
	try {
		var classTemplate = new JSDOC.JsPlate(publish.conf.templatesDir+"class.tmpl");
		var classesTemplate = new JSDOC.JsPlate(publish.conf.templatesDir+"allclasses.tmpl");
	}
	catch(e) {
		print("Couldn't create the required templates: "+e);
		quit();
	}
	
	// some ustility filters
	function hasNoParent($) {return ($.memberOf == "")}
	function isaFile($) {return ($.is("FILE"))}
	function isaClass($) {return ($.is("CONSTRUCTOR") || $.isNamespace)}
	
	// get an array version of the symbolset, useful for filtering
	var symbols = symbolSet.toArray();
	
	// create the hilited source code files
	var files = JSDOC.opt.srcFiles;
 	for (var i = 0, l = files.length; i < l; i++) {
 		var file = files[i];
 		var srcDir = publish.conf.outDir + "symbols/src/";
		makeSrcFile(file, srcDir);
 	}
 	
 	// get a list of all the classes in the symbolset
 	var classes = symbols.filter(isaClass).sort(makeSortby("alias"));
	
	// create a filemap in which outfiles must be to be named uniquely, ignoring case
	if (JSDOC.opt.u) {
		var filemapCounts = {};
		Link.filemap = {};
		for (var i = 0, l = classes.length; i < l; i++) {
			var lcAlias = classes[i].alias.toLowerCase();
			
			if (!filemapCounts[lcAlias]) filemapCounts[lcAlias] = 1;
			else filemapCounts[lcAlias]++;
			
			Link.filemap[classes[i].alias] = 
				(filemapCounts[lcAlias] > 1)?
				lcAlias+"_"+filemapCounts[lcAlias] : lcAlias;
		}
	}
	
	// create a class index, displayed in the left-hand column of every class page
	Link.base = "../";
 	publish.classesIndex = classesTemplate.process(classes); // kept in memory
	
	// create each of the class pages
	for (var i = 0, l = classes.length; i < l; i++) {
		var symbol = classes[i];
		
		symbol.events = symbol.getEvents();   // 1 order matters
		symbol.methods = symbol.getMethods(); // 2
		
		Link.currentSymbol= symbol;
		var output = "";
		output = classTemplate.process(symbol);
		
		IO.saveFile(publish.conf.outDir+"symbols/", ((JSDOC.opt.u)? Link.filemap[symbol.alias] : symbol.alias) + publish.conf.ext, output);
	}
	
	// regenerate the index with different relative links, used in the index pages
	Link.base = "";
	publish.classesIndex = classesTemplate.process(classes);
	
	// create the class index page
	try {
		var classesindexTemplate = new JSDOC.JsPlate(publish.conf.templatesDir+"index.tmpl");
	}
	catch(e) { print(e.message); quit(); }
	
	var classesIndex = classesindexTemplate.process(classes);
	IO.saveFile(publish.conf.outDir, "index"+publish.conf.ext, classesIndex);
	classesindexTemplate = classesIndex = classes = null;
	
	// create the file index page
	try {
		var fileindexTemplate = new JSDOC.JsPlate(publish.conf.templatesDir+"allfiles.tmpl");
	}
	catch(e) { print(e.message); quit(); }
	
	var documentedFiles = symbols.filter(isaFile); // files that have file-level docs
	var allFiles = []; // not all files have file-level docs, but we need to list every one
	
	for (var i = 0; i < files.length; i++) {
		allFiles.push(new JSDOC.Symbol(files[i], [], "FILE", new JSDOC.DocComment("/** */")));
	}
	
	for (var i = 0; i < documentedFiles.length; i++) {
		var offset = files.indexOf(documentedFiles[i].alias);
		allFiles[offset] = documentedFiles[i];
	}
		
	allFiles = allFiles.sort(makeSortby("name"));

	// output the file index page
	var filesIndex = fileindexTemplate.process(allFiles);
	IO.saveFile(publish.conf.outDir, "files"+publish.conf.ext, filesIndex);
	fileindexTemplate = filesIndex = files = null;
	
	// copy static files
	IO.copyFile(publish.conf.templatesDir+"/"+publish.conf.cssDir+"om-core.css", publish.conf.outDir+"/"+publish.conf.cssDir);
	IO.copyFile(publish.conf.templatesDir+"/"+publish.conf.cssDir+"om-theme.css", publish.conf.outDir+"/"+publish.conf.cssDir);
	//added by chenjie
	IO.copyFile(publish.conf.templatesDir+"/"+publish.conf.cssDir+"om-panel.css", publish.conf.outDir+"/"+publish.conf.cssDir);
	IO.copyFile(publish.conf.templatesDir+"/"+publish.conf.cssDir+"om-tabs.css", publish.conf.outDir+"/"+publish.conf.cssDir);
	IO.copyFile(publish.conf.templatesDir+"/"+publish.conf.cssDir+"docs.css", publish.conf.outDir+"/"+publish.conf.cssDir);
	//IO.copyFile(publish.conf.templatesDir+"/"+publish.conf.cssDir+"jquery-ui-1.8.14.custom.css", publish.conf.outDir+"/"+publish.conf.cssDir);
	IO.copyFile(publish.conf.templatesDir+"/"+publish.conf.jsDir+"jquery.js", publish.conf.outDir+"/"+publish.conf.jsDir);
	//IO.copyFile(publish.conf.templatesDir+"/"+publish.conf.jsDir+"jquery-ui-1.8.14.custom.min.js", publish.conf.outDir+"/"+publish.conf.jsDir);
	IO.copyFile(publish.conf.templatesDir+"/"+publish.conf.jsDir+"jquery.ui.core.js", publish.conf.outDir+"/"+publish.conf.jsDir);
	IO.copyFile(publish.conf.templatesDir+"/"+publish.conf.jsDir+"jquery.ui.widget.js", publish.conf.outDir+"/"+publish.conf.jsDir);
	//added by chenjie
	IO.copyFile(publish.conf.templatesDir+"/"+publish.conf.jsDir+"om-panel.js", publish.conf.outDir+"/"+publish.conf.jsDir);
	
	IO.copyFile(publish.conf.templatesDir+"/"+publish.conf.jsDir+"om-tabs.js", publish.conf.outDir+"/"+publish.conf.jsDir);
	
	//added by zhoufazhi
	IO.copyFile(publish.conf.templatesDir+"/"+publish.conf.imagesDir+"bg-demo-docs-param.gif", publish.conf.outDir+"/"+publish.conf.imagesDir);
	IO.copyFile(publish.conf.templatesDir+"/"+publish.conf.imagesDir+"demo-spindown-open.gif", publish.conf.outDir+"/"+publish.conf.imagesDir);
	IO.copyFile(publish.conf.templatesDir+"/"+publish.conf.imagesDir+"demo-spindown-closed.gif", publish.conf.outDir+"/"+publish.conf.imagesDir);
	IO.copyFile(publish.conf.templatesDir+"/"+publish.conf.imagesDir+"ui-bg_flat_75_ffffff_40x100.png", publish.conf.outDir+"/"+publish.conf.imagesDir);
	
	IO.copyFile(publish.conf.templatesDir+"/"+publish.conf.imagesDir+"ui-bg_flat_30_cccccc_40x100.png", publish.conf.outDir+"/"+publish.conf.imagesDir);
	IO.copyFile(publish.conf.templatesDir+"/"+publish.conf.imagesDir+"ui-bg_flat_50_5c5c5c_40x100.png", publish.conf.outDir+"/"+publish.conf.imagesDir);
	IO.copyFile(publish.conf.templatesDir+"/"+publish.conf.imagesDir+"ui-bg_glass_20_555555_1x400.png", publish.conf.outDir+"/"+publish.conf.imagesDir);
	IO.copyFile(publish.conf.templatesDir+"/"+publish.conf.imagesDir+"ui-bg_glass_40_0078a3_1x400.png", publish.conf.outDir+"/"+publish.conf.imagesDir);
	
	IO.copyFile(publish.conf.templatesDir+"/"+publish.conf.imagesDir+"ui-bg_glass_40_ffc73d_1x400.png", publish.conf.outDir+"/"+publish.conf.imagesDir);
	IO.copyFile(publish.conf.templatesDir+"/"+publish.conf.imagesDir+"ui-bg_gloss-wave_25_333333_500x100.png", publish.conf.outDir+"/"+publish.conf.imagesDir);
	IO.copyFile(publish.conf.templatesDir+"/"+publish.conf.imagesDir+"ui-bg_highlight-soft_80_eeeeee_1x100.png", publish.conf.outDir+"/"+publish.conf.imagesDir);
	IO.copyFile(publish.conf.templatesDir+"/"+publish.conf.imagesDir+"ui-bg_inset-soft_25_000000_1x100.png", publish.conf.outDir+"/"+publish.conf.imagesDir);
	
	IO.copyFile(publish.conf.templatesDir+"/"+publish.conf.imagesDir+"ui-bg_inset-soft_30_f58400_1x100.png", publish.conf.outDir+"/"+publish.conf.imagesDir);
	IO.copyFile(publish.conf.templatesDir+"/"+publish.conf.imagesDir+"ui-icons_4b8e0b_256x240.png", publish.conf.outDir+"/"+publish.conf.imagesDir);
	IO.copyFile(publish.conf.templatesDir+"/"+publish.conf.imagesDir+"ui-icons_a83300_256x240.png", publish.conf.outDir+"/"+publish.conf.imagesDir);
	IO.copyFile(publish.conf.templatesDir+"/"+publish.conf.imagesDir+"ui-icons_cccccc_256x240.png", publish.conf.outDir+"/"+publish.conf.imagesDir);
	IO.copyFile(publish.conf.templatesDir+"/"+publish.conf.imagesDir+"ui-icons_ffffff_256x240.png", publish.conf.outDir+"/"+publish.conf.imagesDir);
	
	//added by chenjie
	IO.copyFile(publish.conf.templatesDir+"/"+publish.conf.tabImagesDir+"om-tabs-scroll-left.gif", publish.conf.outDir+"/"+publish.conf.tabImagesDir);
	IO.copyFile(publish.conf.templatesDir+"/"+publish.conf.tabImagesDir+"om-tabs-scroll-right.gif", publish.conf.outDir+"/"+publish.conf.tabImagesDir);
}


/** Just the first sentence (up to a full stop). Should not break on dotted variable names. */
function summarize(desc) {
	if (typeof desc != "undefined")
		return desc.match(/([\w\W]+?\.)[^a-z0-9_$]/i)? RegExp.$1 : desc;
}

/** Make a symbol sorter by some attribute. */
function makeSortby(attribute) {
	return function(a, b) {
		if (a[attribute] != undefined && b[attribute] != undefined) {
			a = a[attribute].toLowerCase();
			b = b[attribute].toLowerCase();
			if (a < b) return -1;
			if (a > b) return 1;
			return 0;
		}
	}
}

/** Pull in the contents of an external file at the given path. */
function include(path) {
	var path = publish.conf.templatesDir+path;
	return IO.readFile(path);
}

/** Turn a raw source file into a code-hilited page in the docs. */
function makeSrcFile(path, srcDir, name) {
	if (JSDOC.opt.s) return;
	
	if (!name) {
		name = path.replace(/\.\.?[\\\/]/g, "").replace(/[\\\/]/g, "_");
		name = name.replace(/\:/g, "_");
	}
	
	var src = {path: path, name:name, charset: IO.encoding, hilited: ""};
	
	if (defined(JSDOC.PluginManager)) {
		JSDOC.PluginManager.run("onPublishSrc", src);
	}

	if (src.hilited) {
		IO.saveFile(srcDir, name+publish.conf.ext, src.hilited);
	}
}

/** Build output for displaying function parameters. */
function makeSignature(params) {
	if (!params) return "()";
	var signature = "("
	+
	params.filter(
		function($) {
			return $.name.indexOf(".") == -1; // don't show config params in signature
		}
	).map(
		function($) {
			return $.name;
		}
	).join(", ")
	+
	")";
	return signature;
}

/** Find symbol {@link ...} strings in text and turn into html links */
function resolveLinks(str, from) {
	str = str.replace(/\{@link ([^} ]+) ?\}/gi,
		function(match, symbolName) {
			return new Link().toSymbol(symbolName);
		}
	);
	
	return str;
}
