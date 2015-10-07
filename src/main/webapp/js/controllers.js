/**
 * O código principal da aplicação em Angular
 */
var cicloviasSPApp = angular.module('OcorrenciasSPApp', []);

// Pego do seguinte site: http://stackoverflow.com/questions/11381673/detecting-a-mobile-browser
window.mobileAndTabletcheck = function() {
	  var check = false;
	  (function(a){if(/(android|bb\d+|meego).+mobile|avantgo|bada\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|iris|kindle|lge |maemo|midp|mmp|mobile.+firefox|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\.(browser|link)|vodafone|wap|windows ce|xda|xiino|android|ipad|playbook|silk/i.test(a)||/1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\-(n|u)|c55\/|capi|ccwa|cdm\-|cell|chtm|cldc|cmd\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\-s|devi|dica|dmob|do(c|p)o|ds(12|\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\-|_)|g1 u|g560|gene|gf\-5|g\-mo|go(\.w|od)|gr(ad|un)|haie|hcit|hd\-(m|p|t)|hei\-|hi(pt|ta)|hp( i|ip)|hs\-c|ht(c(\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\-(20|go|ma)|i230|iac( |\-|\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\/)|klon|kpt |kwc\-|kyo(c|k)|le(no|xi)|lg( g|\/(k|l|u)|50|54|\-[a-w])|libw|lynx|m1\-w|m3ga|m50\/|ma(te|ui|xo)|mc(01|21|ca)|m\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\-2|po(ck|rt|se)|prox|psio|pt\-g|qa\-a|qc(07|12|21|32|60|\-[2-7]|i\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\-|oo|p\-)|sdk\/|se(c(\-|0|1)|47|mc|nd|ri)|sgh\-|shar|sie(\-|m)|sk\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\-|v\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\-|tdg\-|tel(i|m)|tim\-|t\-mo|to(pl|sh)|ts(70|m\-|m3|m5)|tx\-9|up(\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\-|your|zeto|zte\-/i.test(a.substr(0,4)))check = true})(navigator.userAgent||navigator.vendor||window.opera);
	  return check;
	}

/** ***** CONSTANTES ****** */
var ANOS = [2015, 2014, 2013];
var MESES = [ "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
		"Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro" ];

cicloviasSPApp.controller('OcorrenciasSPController', function($scope, $http) {
	/** ***** INICIALIZAÇÔES ESTÁTICAS ****** */
	$scope.anos = ANOS;
	$scope.meses = MESES;
	$scope.anoSelecionado = ANOS[0];
	$scope.mesSelecionado = MESES[0];
	$scope.evolucao = true;
	// TODO: Melhorar isso e extrair para constantes no painel - evitar hard code espalhado
	$scope.buscaSelecionada = "buscaPorDelegacia";
	/** ***** INICIALIZAÇÔES ****** */
	$http.get("rest/estado/SP/municipios").success(function(dados) {
		$scope.municipios = dados;
		$scope.municipioSelecionado = dados[0];
		$scope.carregaDelegacias();
	});	
	$http.get("rest/naturezas").success(function(dados) {
		$scope.naturezas = dados;
		$scope.naturezaSelecionada = dados[0];
	});	

	/** ***** INICIALIZAÇÔES DE ELEMENTOS ****** */
	$('#lblCarregar').each(function() {
		var elem = $(this);
		setInterval(function() {
			elem.fadeToggle(600);
		}, 400);
	});
	/** ***** LISTENERS ****** */
	$scope.carregaDelegacias = function() {
		var idMun = $scope.municipioSelecionado.id;
		var url = "rest/municipio/" +idMun+ "/delegacias";
		$http.get(url).success(function(dados) {
			$scope.delegacias = dados;
			$scope.delegaciaSelecionada = dados[0];
		});
	};
	$scope.trocaEvolucao = function() {
		$scope.evolucao = !$scope.evolucao;
	}
	$scope.carregaDados = function() {
		var url;
		// funcao que monta um objeto do tipo dadosGrafico que tem:
		// titulo, tituloY, series e categorias
		var funcao;
		var busca = $scope.buscaSelecionada;
		var municipioId = $scope.municipioSelecionado.id;
		var ano = $scope.anoSelecionado;
		var mes = $scope.mesSelecionado;
		var indiceMes =  $scope.meses.indexOf(mes) + 1;
		if(busca == "buscaPorDelegacia") {
			var delegaciaId = $scope.delegaciaSelecionada.id;
			if($scope.evolucao) {
				url = "rest/ocorrencia/municipio/"+municipioId+"/delegacia/"+delegaciaId+"/data/"+ano;
				funcao = evolucaoAnualPorDelegacia;
			} else {				
				url = "rest/ocorrencia/municipio/"+municipioId+"/delegacia/"+delegaciaId+"/data/"+ano+"/"+indiceMes;
				funcao = dadosBuscaPorDelegacia;
			}
		}
		if(busca == "buscaPorNatureza") {
			var naturezaId =  $scope.naturezaSelecionada.id;
			if($scope.evolucao) {
				url = "rest/ocorrencia/municipio/"+municipioId+"/natureza/"+naturezaId+"/data/"+ano;
				funcao = evolucaoAnualPorNatureza;
			} else {
				url = "rest/ocorrencia/municipio/"+municipioId+"/natureza/"+naturezaId+"/data/"+ano+"/"+indiceMes;
				funcao = dadosBuscaPorNatureza;			
			}
			
		}
		$http.get(url).success(function(dados) {
			 montaGrafico(funcao(dados, ano, mes));
		});
	};
	$('#abasBuscas a').click(function (e) {
		$scope.buscaSelecionada = this.id;		
	});
});

// TODO: Poderiamos eliminar código repetitivo
function dadosBuscaPorDelegacia(dados, ano, mes) {
	var dadosGrafico = {};
	dadosGrafico.titulo = "Ocorrências da " + dados[0].delegacia.nome + " em " + mes + " de " + ano; 
	dadosGrafico.tituloY = "Total de Ocorrências"
	dadosGrafico.categorias = [];
	dadosGrafico.tipo = "column";
	dadosGrafico.series = [] ;
	dadosGrafico.categorias.push(dados[0].delegacia.nome);
	for(i in dados) {
		var d = dados[i];
		if(d.total == 0) continue;
		dadosGrafico.series.push({
			name: d.natureza.nome,
			data: [d.total]
		});
	};
	return dadosGrafico;
}

function evolucaoAnualPorDelegacia(dados, ano, mes) {
	var dadosGrafico = {};
	dadosGrafico.titulo = "Evolução das ocorrências regisradas na delegacia " + dados[0].delegacia.nome + " em " + ano; 
	dadosGrafico.tituloY = "Total de Ocorrências"
	dadosGrafico.categorias = [];
	dadosGrafico.tipo = "line";
	dadosGrafico.series = [] ;
	// coleta categorias nos meses (os dados são ordenados por data)
	for(i in dados) {
		var d = dados[i];
		var posMes = parseInt(d.data.substring(0, 2)) - 1;
		var cat = MESES[posMes];
		if(dadosGrafico.categorias.indexOf(cat) == -1) {			
			dadosGrafico.categorias.push(cat);
		}
	};
	// coleta os dados para as séries (que são por naturezas)
	for(i in dados) {
		var d = dados[i];
		var serie = $.grep(dadosGrafico.series, function(s){ return s.name == d.natureza.nome; });
		if(serie.length == 0) {
			serie  = {
					name: d.natureza.nome,
					data: []
			};
			dadosGrafico.series.push(serie);
		} else {
			serie = serie[0];
		}
		serie.data.push(d.total);
	}
	return dadosGrafico;
}

function evolucaoAnualPorNatureza(dados, ano, mes) {
	var dadosGrafico = {};
	dadosGrafico.titulo = "Evolução das ocorrëncias da natureza " + dados[0].natureza.nome + " em " + ano; 
	dadosGrafico.tituloY = "Total de Ocorrências"
	dadosGrafico.categorias = [];
	dadosGrafico.tipo = "line";
	dadosGrafico.series = [] ;
	// coleta categorias nos meses (os dados são ordenados por data)
	for(i in dados) {
		var d = dados[i];
		var posMes = parseInt(d.data.substring(0, 2)) - 1;
		var cat = MESES[posMes];
		if(dadosGrafico.categorias.indexOf(cat) == -1) {			
			dadosGrafico.categorias.push(cat);
		}
	};
	// coleta os dados para as séries (que são por delegacias)
	for(i in dados) {
		var d = dados[i];
		var serie = $.grep(dadosGrafico.series, function(s){ return s.name == d.delegacia.nome; });
		if(serie.length == 0) {
			serie  = {
					name: d.delegacia.nome,
					data: []
			};
			dadosGrafico.series.push(serie);
		} else {
			serie = serie[0];
		}
		serie.data.push(d.total);
	}
	return dadosGrafico;
}

function dadosBuscaPorNatureza(dados, ano, mes) {
	var dadosGrafico = {};
	var data = mes  ? mes + " de " + ano : ano;
	dadosGrafico.titulo = "Ocorrências da natureza " + dados[0].natureza.nome + " em " + data; 
	dadosGrafico.tituloY = "Total de Ocorrências"
	dadosGrafico.tipo = "column";
	dadosGrafico.categorias = [];
	dadosGrafico.series = [] ;
	dadosGrafico.categorias.push(dados[0].natureza.nome);
	for(i in dados) {
		var d = dados[i];
		dadosGrafico.series.push({
			name: d.delegacia.nome,
			data: [d.total]
		});
	}
	return dadosGrafico;
}
function montaGrafico(dadosGrafico) {
	$("#grafico").highcharts({
		 chart: {
	            type: dadosGrafico.tipo
	        },
		title : {
			text : dadosGrafico.titulo
		},
		yAxis : {
			title : {
				text : dadosGrafico.tituloY
			},
			min : 0
		},
		xAxis : {
			categories : dadosGrafico.categorias,
		},
		plotOptions : {
			series : {
				allowPointSelect : true
			}
		},
		legend : {
			itemWidth : 200,
			layout : 'vertical',
			align : 'right',
			verticalAlign : 'middle',
			borderWidth : 0
		},
		series : dadosGrafico.series
	});
};