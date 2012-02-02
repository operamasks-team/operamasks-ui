(function($){
    module( "omGrid: methods");
    test( "{ onRowSelect事件}", function() {
        expect(1);
        $('#onRowSelect').omGrid({
            dataSource : 'griddata.do?method=fast',
            onRowSelect : fn,
            colModel : [ {header : 'ID', name : 'id', width : 100, align : 'center', sortable : true}, 
                         {header : '地区', name : 'city', width : 60, align : 'left', sortable : true}, 
                         {header : '地址', name : 'address', align : 'left', width : 'autoExpand', autoExpandMin : 150, sortable : true} ]
        });
        var ind;
        function fn(grid,rowIndex,rowData){
            ind = rowIndex;
            fnn(ind);
        }
        stop();
        setTimeout(function(){
            $("#onRowSelect tr").eq(0).click();
        },500);
        
        function fnn(ind){
            equal(ind != null,true,"行选中事件执行正确");
            start();
        }
    });
    test( "{ onRowDeselect事件}", function() {
        expect(1);
        $('#onRowDeselect').omGrid({
            dataSource : 'griddata.do?method=fast',
            onRowDeselect : function (grid,rowIndex,rowData){
                ind = rowIndex;
                fnn(ind);
            },
            colModel : [ {header : 'ID', name : 'id', width : 100, align : 'center', sortable : true}, 
                         {header : '地区', name : 'city', width : 60, align : 'left', sortable : true}, 
                         {header : '地址', name : 'address', align : 'left', width : 'autoExpand', autoExpandMin : 150, sortable : true} ]
        });
        var ind;
        stop();
        setTimeout(function(){
            $("#onRowDeselect tr").eq(0).click();
        },500);
        setTimeout(function(){
            $("#onRowDeselect tr").eq(1).click();
        },1000);
        function fnn(ind){
            equal(ind != null,true,"行反选正确");
            start();
        }
    });
    test( "{ onRowClick事件}", function() {
        expect(1);
        $('#onRowClick').omGrid({
            dataSource : 'griddata.do?method=fast',
            onRowClick : function (grid,rowIndex,rowData){
                ind = rowIndex;
                fnn(ind);
            },
            colModel : [ {header : 'ID', name : 'id', width : 100, align : 'center', sortable : true}, 
                         {header : '地区', name : 'city', width : 60, align : 'left', sortable : true}, 
                         {header : '地址', name : 'address', align : 'left', width : 'autoExpand', autoExpandMin : 150, sortable : true} ]
        });
        var ind;
        stop();
        setTimeout(function(){
            $("#onRowClick tr").eq(0).click();
        },500);
        function fnn(ind){
            equal(ind != null,true,"行点击事件正确执行");
            start();
        }
    });
    test( "{ onRowDblClick事件}", function() {
        expect(1);
        $('#onRowDblClick').omGrid({
            dataSource : 'griddata.do?method=fast',
            onRowDblClick : function (grid,rowIndex,rowData){
                ind = rowIndex;
                fnn(ind);
            },
            colModel : [ {header : 'ID', name : 'id', width : 100, align : 'center', sortable : true}, 
                         {header : '地区', name : 'city', width : 60, align : 'left', sortable : true}, 
                         {header : '地址', name : 'address', align : 'left', width : 'autoExpand', autoExpandMin : 150, sortable : true} ]
        });
        var ind;
        stop();
        setTimeout(function(){
            $("#onRowDblClick tr").eq(0).dblclick();
        },500);
        function fnn(ind){
            equal(ind != null,true,"行双击事件执行正确");
            start();
        }
    });
    test( "{ onPageChange事件}", function() {
        expect(1);
        $('#onPageChange').omGrid({
            dataSource : 'griddata.do?method=fast',
            onPageChange : fn,
            colModel : [ {header : 'ID', name : 'id', width : 100, align : 'center', sortable : true}, 
                         {header : '地区', name : 'city', width : 60, align : 'left', sortable : true}, 
                         {header : '地址', name : 'address', align : 'left', width : 'autoExpand', autoExpandMin : 150, sortable : true} ]
        });
        var ind;
        function fn(type,nowPage){
            fnn(nowPage);
        }
        stop();
        setTimeout(function(){
            $("#onPageChange").parent().next().children().children().find(".pNext").click();
        },500);
        function fnn(ind){
            equal(ind,2,"翻页事件执行正确");
            start();
        }
    });
    test( "{ onSuccess事件}", function() {
        expect(1);
        $('#onLoadSuccess').omGrid({
            dataSource : 'griddata.do?method=fast',
            onSuccess : fn,
            limit : 2,
            colModel : [ {header : 'ID', name : 'id', width : 100, align : 'center', sortable : true}, 
                         {header : '地区', name : 'city', width : 60, align : 'left', sortable : true}, 
                         {header : '地址', name : 'address', align : 'left', width : 'autoExpand', autoExpandMin : 150, sortable : true} ]
        });
        var ind;
        function fn(data,testStatus,XMLHttpRequest){
            ind = data.rows.length;
        }
        stop();
        setTimeout(function(){
            equal(ind,2,"数据加载事件正确执行");  //设置每页2条数据
            start();
        },500);
    });
    test( "{ onError事件}", function() {
        expect(1);
        var mark;
        $('#onError').omGrid({
            dataSource : 'griddata.do1?method=fast1',
            onError : function(grid,error,request){
                mark = error;
            },
            colModel : [ {header : 'ID', name : 'id', width : 100, align : 'center', sortable : true}, 
                         {header : '地区', name : 'city', width : 60, align : 'left', sortable : true}, 
                         {header : '地址', name : 'address', align : 'left', width : 'autoExpand', autoExpandMin : 150, sortable : true} ]
        });
        stop();
        setTimeout(function(){
            equal(mark,"error","onError事件执行正确"); 
            start();
        },500);
    });
    test( "{ onRefresh事件}", function() {
        expect(1);
        var mark;
        $('#onRefresh').omGrid({
            dataSource : 'griddata.do?method=fast',
            onRefresh : function(grid,pageRecords){
                $('#onRefresh').omGrid("setSelections",[2,4]);
            },
            singleSelect : false,
            colModel : [ {header : 'ID', name : 'id', width : 100, align : 'center', sortable : true}, 
                         {header : '地区', name : 'city', width : 60, align : 'left', sortable : true}, 
                         {header : '地址', name : 'address', align : 'left', width : 'autoExpand', autoExpandMin : 150, sortable : true} ]
        });
        stop();
        setTimeout(function(){
            var hasSelected2 = $("#onRefresh tr").eq(2).children(".checkboxCol").children().children().hasClass("selected");
            var hasSelected4 = $("#onRefresh tr").eq(4).children(".checkboxCol").children().children().hasClass("selected");
            equal((hasSelected2 && hasSelected4),true,"onRefresh事件执行正确"); 
            start();
        },500);
    });
   
}(jQuery));