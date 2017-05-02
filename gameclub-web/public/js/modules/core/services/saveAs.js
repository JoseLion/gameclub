angular.module("Core").factory('saveAs', function() {
	return function(fileData, type, name) {
		let blob = new Blob([fileData], {type: type});
		let blobUrl = URL.createObjectURL(blob);
		let anchor = document.createElement("a");

		if (name != null) {
			anchor.download = name;
		}

		anchor.href = blobUrl;
		document.body.appendChild(anchor)
		anchor.click();
		delete anchor;
	}
});