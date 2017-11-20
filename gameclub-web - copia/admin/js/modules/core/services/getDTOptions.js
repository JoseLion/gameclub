angular.module("Core").factory('getDTOptions', function(DTOptionsBuilder, DTColumnDefBuilder, Const, forEach) {
	return {
		unpaged: function(htmlOptions) {
			if (htmlOptions == null) {
				htmlOptions = "Tgitp";
			}

			return DTOptionsBuilder.newOptions()
				.withDOM('<"html5buttons"B>' + htmlOptions)
				.withButtons([])
				.withDisplayLength(Const.tableSize)
				.withOption('order', []);
		},

		paged: function(htmlOptions) {
			if (htmlOptions == null) {
				htmlOptions = "Tgit";
			}

			return DTOptionsBuilder.newOptions()
				.withDOM('<"html5buttons"B>' + htmlOptions)
				.withButtons([])
				.withDisplayLength(Const.tableSize)
				.withOption('order', []);
		},

		infoCallback: function(totalElements, begining, end) {
			if (totalElements == null) {
				return "Mostrando...";
			} else {
				if (totalElements == 0) {
					return "La tabla no contiene ningún registro";
				} else {
					return "Mostrando " + begining + " al " + end + " de " + totalElements + " registros";
				}
			};
		},

		notSortable: function(columns) {
			let defsArray = [
				DTColumnDefBuilder.newColumnDef(columns).notSortable()
			];
			return defsArray;
		},

		notSortableAll: function(quantity) {
			let defsArray = [];
			for(let i = 0 ; i<quantity ; i++) {
				defsArray.push(DTColumnDefBuilder.newColumnDef(i).notSortable());
			}
			return defsArray;
		}
	};
});
