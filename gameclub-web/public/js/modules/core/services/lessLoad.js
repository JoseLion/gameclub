angular.module('Core').factory('lessLoad', function() {
	return {
		add: function(lessFile) {
			if($('link[href="'.concat(lessFile).concat('"]')).length == 0) {
				$('head').append('<link rel="stylesheet/less" href="'.concat(lessFile).concat('" />'));
				less.sheets.push($('link[href="'.concat(lessFile).concat('"]'))[0]);
				less.refresh();
			}
		}
	};
});
