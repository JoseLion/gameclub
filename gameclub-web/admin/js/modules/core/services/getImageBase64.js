angular.module("Core").service('getImageBase64', function() {
	return function (buffer, type) {
		let binary = '';
		let bytes = new Uint8Array(buffer);

		for (let i = 0; i < bytes.byteLength; i++) {
			binary += String.fromCharCode(bytes[i]);
		}

		return "data:" + (type != null ? type : 'image/png') + ";base64," + btoa(binary);
	}
});