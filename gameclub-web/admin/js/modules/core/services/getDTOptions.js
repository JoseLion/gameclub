angular.module("Core").factory('getDTOptions', function(DTOptionsBuilder, DTColumnDefBuilder, Const, forEach) {
	return {
		unpaged: function(htmlOptions) {
			if (htmlOptions == null) {
				htmlOptions = "Tgitp";
			}

			return DTOptionsBuilder.newOptions()
				.withDOM('<"html5buttons">B' + htmlOptions)
				.withButtons([])
				.withDisplayLength(Const.tableSize)
				.withOption('order', []);
		},

		paged: function(htmlOptions) {
			if (htmlOptions == null) {
				htmlOptions = "Tgit";
			}

			return DTOptionsBuilder.newOptions()
				.withDOM('<"html5buttons">B' + htmlOptions)
				.withButtons([])
				.withDisplayLength(Const.tableSize)
				.withOption('order', []);
		},

		pagedWithButtons: function(htmlOptions) {
			if (htmlOptions == null) {
				htmlOptions = "Tgit";
			}

			return DTOptionsBuilder.newOptions()
				.withDOM('<"html5buttons">B' + htmlOptions)
				.withButtons([{
					extend: 'copyHtml5',
					text: '<i class="fa fa-files-o" style="color:orange;"><b>C</b>opiar</i>',
					key: {
						key: 'c',
						altkey: true
					},
					titleAttr: 'Copiar',
					title: 'GameClub-LogTracking-Report'
					
				},{
					extend: 'excelHtml5',
					text: '<i class="fa fa-file-excel-o" style="color:green;">E<b>x</b>cel</i>',
					key: {
						key: 'x',
						altkey: true
					},
					title: 'GameClub-LogTracking-Report',
					orientation: 'landscape',
              		pageSize: "A3"
				},{
					extend: 'print',
					text: '<i class="fa fa-print" style="color:black;">Imprimir</i>',
					titleAttr: 'Imprimir',
					title: 'GameClub-LogTracking-Report',
					autoPrint: true,
					orientation: 'landscape',
              		pageSize: "A3",
					customize: function (win) {
						$(win.document.body).find('table').addClass('display').css('font-size', '9px');
						$(win.document.body).find('tr:nth-child(odd) td').each(function(index){
							$(this).css('background-color','#D0D0D0');
						});
						$(win.document.body).find('h1').css('text-align','center');
						$(win.document.body).css( 'font-size', '9pt' )
                        .prepend(
                            '<img src="https://beta.gameclub.com.ec/img/gameclub-footer-logo.svg" style="position:relative; float: left; top:0; left:0; opacity: 80; width: 80px; height: 100px; " />'
                        ); 
						$(win.document.body).find( 'table' )
							.addClass( 'compact' )
							.css( 'font-size', 'inherit' );
					}
					 
				}])
				.withDisplayLength(Const.tableSize)
				.withOption('order', [])
				.withLanguage({
					buttons: {
						copySuccess: {
							1: "Copiada una fila al portapapeles",
							_: "Copiadas %d filas al portapapeles"
						},
						copyTitle: 'Copiar al portapapeles',
						copyKeys: 'Presione <i>ctrl</i> o <i>\u2318</i> + <i>C</i> para copiar los datos de la tabla<br>al portapapeles.<br><br>Para cancelar, click en este mensaje o fuera.'
					}
				});
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
