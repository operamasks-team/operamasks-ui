(function($) {

function shouldmove(why) {
	drag(el, 50, 50);
	moved(50, 50, why);
}

function shouldnotmove(why) {
	drag(el, 50, 50);
	moved(0, 0, why);
}

module("omDraggable: methods");

test("init", function() {
	expect(5);

	$("<div></div>").appendTo('body').omDraggable().remove();
	ok(true, '.omDraggable() called on element');

	$([]).omDraggable();
	ok(true, '.omDraggable() called on empty collection');

	$("<div></div>").omDraggable();
	ok(true, '.omDraggable() called on disconnected DOMElement');

	$("<div></div>").omDraggable().omDraggable("foo");
	ok(true, 'arbitrary method called after init');

	$("<div></div>").omDraggable().omDraggable("option", "foo");
	ok(true, 'arbitrary option getter after init');

});

test("destroy", function() {
	$("<div></div>").appendTo('body').omDraggable().omDraggable("destroy").remove();
	ok(true, '.omDraggable("destroy") called on element');

	$([]).omDraggable().omDraggable("destroy");
	ok(true, '.omDraggable("destroy") called on empty collection');

	$("<div></div>").omDraggable().omDraggable("destroy");
	ok(true, '.omDraggable("destroy") called on disconnected DOMElement');

	$("<div></div>").omDraggable().omDraggable("destroy").omDraggable("foo");
	ok(true, 'arbitrary method called after destroy');

	var expected = $('<div></div>').omDraggable(),
		actual = expected.omDraggable('destroy');
	equals(actual, expected, 'destroy is chainable');
});

test("enable", function() {
	expect(5);
	el = $("#draggable2").omDraggable({ disabled: true });
	shouldnotmove('.omDraggable({ disabled: true })');
	el.omDraggable("enable");
	shouldmove('.omDraggable("enable")');
	equals(el.omDraggable("option", "disabled"), false, "disabled option getter");

	el.omDraggable("destroy");
	el.omDraggable({ disabled: true });
	shouldnotmove('.omDraggable({ disabled: true })');
	
	var expected = $('<div></div>').omDraggable(),
		actual = expected.omDraggable('enable');
	equals(actual, expected, 'enable is chainable');
});

test("disable", function() {
	expect(5);
	el = $("#draggable2").omDraggable({ disabled: false });
	shouldmove('.omDraggable({ disabled: false })');
	el.omDraggable("disable");
	shouldnotmove('.omDraggable("disable")');
	equals(el.omDraggable("option", "disabled"), true, "disabled option getter");

	el.omDraggable("destroy");

	el.omDraggable({ disabled: false });
	shouldmove('.omDraggable({ disabled: false })');
	
	var expected = $('<div></div>').omDraggable(),
		actual = expected.omDraggable('disable');
	equals(actual, expected, 'disable is chainable');
});

})(jQuery);
