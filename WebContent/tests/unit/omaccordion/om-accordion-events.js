(function($) {

	module("omAccordion: events");

	asyncTest("Activate", function() {
		expect(2);
		var element = $("#accordionId").omAccordion({
			collapsible : true
		});
		var headers = element.find(".om-panel-header");
		element.omAccordion({
			onActivate : function(index) {
				equal(headers.eq(1).hasClass("om-panel-expanded"), true,"Activate event is triggered");
				start();
			}
		});
		element.omAccordion("activate", "1");
		accordion_state(element, 0, 1, 0);
	});

	asyncTest("beforeActivate", function() {
		expect(2);
		var element = $("#accordionId").omAccordion({
			collapsible : true
		});
		var headers = element.find(".om-panel-header");
		element.omAccordion("activate", "1");// 由于目前组件onBeforeSelect事件只在accordion展开时触发，所以需要此操作
		element.omAccordion({
			onBeforeActivate : function(index) {
				equal(headers.eq(0).hasClass("om-panel-collapsed"), true,"BeforeActivate event is triggered");
				start();
			}
		});
		element.omAccordion("activate", "0");
		accordion_state(element, 1, 0, 0);
	});

	asyncTest("Collaspe", function() {
		expect(2);
		var element = $("#accordionId").omAccordion({
			collapsible : true
		});
		var headers = element.find(".om-panel-header");
		element.omAccordion("activate", "1");
		element.omAccordion({
			onCollapse : function(index) {
				equal(headers.eq(2).hasClass("om-panel-collapsed"), true,"Collapse event is triggered");
				start();
			}
		});
		element.omAccordion("activate", "2");		
		accordion_state(element, 0, 0, 1);

	});

	asyncTest(" onBeforeCollapse ", function() {
		expect(2);
		var element = $("#accordionId").omAccordion({
			collapsible : true
		});
		var headers = element.find(".om-panel-header");
		element.omAccordion({
			onBeforeCollapse : function(index) {
				equal(headers.eq(1).hasClass("om-panel-collapsed"), true,"beforeCollapse event is triggered");
				start();
			}
		});
		element.omAccordion("activate", "1");		
		accordion_state(element, 0,1,0);
	});

}(jQuery));
