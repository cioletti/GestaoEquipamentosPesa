package bean
{
	import mx.collections.ArrayCollection;

	[RemoteClass(alias="com.gestaoequipamentos.bean.ChekinBean")]
	public class CheckinBean
	{
		private var _id:Number;
		private var _data:String;
		private var _modelo:String;
		private var _numeroOs:String;
		private var _cliente:String;
		private var _contato:String;
		private var _telefone:String;
		private var _tecnico:String;
		private var _horimetro:String;
		private var _familia:String;
		private var _idFamilia:Number;
		private var _maquina:String;
		private var _serie:String;
		private var _equipamento:String;
		private var _codCliente:String;
		private var _filial:String;
		private var _hasSendDbs:String;
		private var _hasSendDataOrcamento:String;
		private var _hasConclusaoServico:String;
		private var _idOsPalm:Number;
		private var _idModelo:Number;
		private var _idPrefixo:Number;
		private var _hasSendDataAprovacao:String;
		private var _dataPrevisaoEntrega:String;
		private var _hasSendDataPrevisao:String;
		private var _hasSendPecasDbs:String;
		private var _tipoCliente:String;
		private var _siglaIndicadorGarantia:String;
		private var _tipoOperacao:String;
		private var _tipoRejeicao:String;
		private var _obs:String;
		private var _isRemoveSegmento:String;
		private var _nomeImagemStatus:String;
		private var _obsCrm:String;
		private var _statusCrm:String;
		private var _obsOs:String;
		private var _validadeProposta:Number;
		private var _idTipoFrete:Number;
		private var _descricaoTipoFrete:String;
		private var _servicoTerceirosList:ArrayCollection;
		private var _printPdf:String;
		private var _isLiberadoPDigitador:String;
		private var _idEquipamento:String;
		private var _isLiberadoPOrcamentista:String;
		private var _isLiberadoPConsultor:String;
		private var _statusNegociacaoConsultor:String;
		private var _obsNegociacaoConsultor:String;
		private var _idFuncionarioConsultor:String;
		private var _codErroDbs:String;
		private var _dataOrcamento:String;
		private var _descricaoNegociacaoConsultor:String;
		private var _jobControl:String;
		private var _dataAprovacao:String;
		private var _dataTerminoServico:String;
		private var _dataFaturamento:String;
		private var _obsProposta:String;
		private var _backgroundColor:String;
		private var _dataLiberacaoPecas:String;
		private var _hasDataEntregaPedidos:String;
		private var _isOpenOs:String;
		private var _isMoreThirtyDay:String;
		private var _siglaPerfil:String;
		private var _idSegmento:Number;
		private var _obsJobControl:String;
		private var _PIPNO:String;
		
		
		private var _moMiscDesl:String;
		private var _valorPecas:String;
		private var _valorTotal:String;
		private var _condicaoPagamento:String;
		private var _propNumero:String;
		private var _autPor:String;
		private var _ordemCompra:String;
		private var _preenchidoPor:String;
		private var _obsNf:String;
		//private var _obs:String;
		
		
		private var _ordemCompraPeca:String;
		private var _ordemCompraServico:String;
		
		private var _estabelecimentoCredorPecas:String;
		private var _estabelecimentoCredorServicos:String;
		
		private var _condicaoPagamentoPecas:String;
		private var _condicaoPagamentoServicos:String;
		
		private var _descPorcPecas:String;
		private var _descPorcServicos:String;
		
		private var _descValorPecas:String;
		private var _descValorServicos:String;
		
		private var _automaticaFaturada:String;
		
		private var _obsPeca:String;
		private var _obsServico:String;
		
		private var _condEspecial:String;
		private var _encerrarAutomatica:String;
		
		private var _observacaoDesconto:String;
		private var _isAprovadoOs:String;
		
		private var _filialStr:String;
		
		
		public function get dataLiberacaoPecas():String
		{
			return _dataLiberacaoPecas;
		}

		public function set dataLiberacaoPecas(value:String):void
		{
			_dataLiberacaoPecas = value;
		}

		public function get dataFaturamento():String
		{
			return _dataFaturamento;
		}

		public function set dataFaturamento(value:String):void
		{
			_dataFaturamento = value;
		}

		public function get dataTerminoServico():String
		{
			return _dataTerminoServico;
		}

		public function set dataTerminoServico(value:String):void
		{
			_dataTerminoServico = value;
		}

		public function get dataAprovacao():String
		{
			return _dataAprovacao;
		}

		public function set dataAprovacao(value:String):void
		{
			_dataAprovacao = value;
		}

		public function get descricaoNegociacaoConsultor():String
		{
			return _descricaoNegociacaoConsultor;
		}

		public function set descricaoNegociacaoConsultor(value:String):void
		{
			_descricaoNegociacaoConsultor = value;
		}

		public function get dataOrcamento():String
		{
			return _dataOrcamento;
		}

		public function set dataOrcamento(value:String):void
		{
			_dataOrcamento = value;
		}

		public function get codErroDbs():String
		{
			return _codErroDbs;
		}

		public function set codErroDbs(value:String):void
		{
			_codErroDbs = value;
		}

		public function get printPdf():String
		{
			return _printPdf;
		}

		public function set printPdf(value:String):void
		{
			_printPdf = value;
		}

		public function get servicoTerceirosList():ArrayCollection
		{
			return _servicoTerceirosList;
		}

		public function set servicoTerceirosList(value:ArrayCollection):void
		{
			_servicoTerceirosList = value;
		}

		public function get idTipoFrete():Number
		{
			return _idTipoFrete;
		}

		public function set idTipoFrete(value:Number):void
		{
			_idTipoFrete = value;
		}

		public function get validadeProposta():Number
		{
			return _validadeProposta;
		}

		public function set validadeProposta(value:Number):void
		{
			_validadeProposta = value;
		}

		public function get obsOs():String
		{
			return _obsOs;
		}

		public function set obsOs(value:String):void
		{
			_obsOs = value;
		}

		public function get statusCrm():String
		{
			return _statusCrm;
		}

		public function set statusCrm(value:String):void
		{
			_statusCrm = value;
		}

		public function get obsCrm():String
		{
			return _obsCrm;
		}

		public function set obsCrm(value:String):void
		{
			_obsCrm = value;
		}

		public function get isRemoveSegmento():String
		{
			return _isRemoveSegmento;
		}

		public function set isRemoveSegmento(value:String):void
		{
			_isRemoveSegmento = value;
		}

		public function get id(): Number{return _id};
		public function set id(id: Number): void{this._id = id}; 
		
		public function get data(): String{return _data};
		public function set data(data: String): void{this._data = data}; 

		public function get modelo(): String{return _modelo};
		public function set modelo(modelo: String): void{this._modelo = modelo};
		
		public function get numeroOs(): String{return _numeroOs};
		public function set numeroOs(numeroOs: String): void{this._numeroOs = numeroOs}; 
		
		public function get cliente(): String{return _cliente};
		public function set cliente(cliente: String): void{this._cliente = cliente};
		
		public function get contato(): String{return _contato};
		public function set contato(contato: String): void{this._contato = contato};
		
		public function get telefone(): String{return _telefone};
		public function set telefone(telefone: String): void{this._telefone = telefone}; 
		
		public function get tecnico(): String{return _tecnico};
		public function set tecnico(tecnico: String): void{this._tecnico = tecnico}; 
		
		public function get horimetro(): String{return _horimetro};
		public function set horimetro(horimetro: String): void{this._horimetro = horimetro};
		
		public function get familia(): String{return _familia};
		public function set familia(familia: String): void{this._familia = familia};
		
		public function get idFamilia(): Number{return _idFamilia};
		public function set idFamilia(idFamilia: Number): void{this._idFamilia = idFamilia};
		
		public function get maquina(): String{return _maquina};
		public function set maquina(maquina: String): void{this._maquina = maquina}; 
		
		public function get serie(): String{return _serie};
		public function set serie(serie: String): void{this._serie = serie}; 
		
		public function get equipamento(): String{return _equipamento};
		public function set equipamento(equipamento: String): void{this._equipamento = equipamento}; 
		
		public function get codCliente(): String{return _codCliente};
		public function set codCliente(codCliente: String): void{this._codCliente = codCliente}; 
		
		public function get filial(): String{return _filial};
		public function set filial(filial: String): void{this._filial = filial}; 
		
		public function get hasSendDbs(): String{return _hasSendDbs};
		public function set hasSendDbs(hasSendDbs: String): void{this._hasSendDbs = hasSendDbs};
		
		public function get hasSendDataOrcamento(): String{return _hasSendDataOrcamento};
		public function set hasSendDataOrcamento(hasSendDataOrcamento: String): void{this._hasSendDataOrcamento = hasSendDataOrcamento}; 

		public function get hasConclusaoServico(): String{return _hasConclusaoServico};
		public function set hasConclusaoServico(hasConclusaoServico: String): void{this._hasConclusaoServico = hasConclusaoServico}; 
		
		public function get idOsPalm(): Number{return _idOsPalm};
		public function set idOsPalm(idOsPalm: Number): void{this._idOsPalm = idOsPalm}; 

		public function get idModelo(): Number{return _idModelo};
		public function set idModelo(idModelo: Number): void{this._idModelo = idModelo}; 

		public function get idPrefixo(): Number{return _idPrefixo};
		public function set idPrefixo(idPrefixo: Number): void{this._idPrefixo = idPrefixo}; 
		
		public function get hasSendDataAprovacao(): String{return _hasSendDataAprovacao};
		public function set hasSendDataAprovacao(hasSendDataAprovacao: String): void{this._hasSendDataAprovacao = hasSendDataAprovacao}; 

		public function get dataPrevisaoEntrega(): String{return _dataPrevisaoEntrega};
		public function set dataPrevisaoEntrega(dataPrevisaoEntrega: String): void{this._dataPrevisaoEntrega = dataPrevisaoEntrega}; 
		
		public function get hasSendDataPrevisao(): String{return _hasSendDataPrevisao};
		public function set hasSendDataPrevisao(hasSendDataPrevisao: String): void{this._hasSendDataPrevisao = hasSendDataPrevisao}; 

		public function get hasSendPecasDbs(): String{return _hasSendPecasDbs};
		public function set hasSendPecasDbs(hasSendPecasDbs: String): void{this._hasSendPecasDbs = hasSendPecasDbs}; 
		
		public function get tipoCliente(): String{return _tipoCliente};
		public function set tipoCliente(tipoCliente: String): void{this._tipoCliente = tipoCliente}; 

		public function get siglaIndicadorGarantia(): String{return _siglaIndicadorGarantia};
		public function set siglaIndicadorGarantia(siglaIndicadorGarantia: String): void{this._siglaIndicadorGarantia = siglaIndicadorGarantia};
		
		public function get tipoOperacao(): String{return _tipoOperacao};
		public function set tipoOperacao(tipoOperacao: String): void{this._tipoOperacao = tipoOperacao}; 
		
		public function get tipoRejeicao(): String{return _tipoRejeicao};
		public function set tipoRejeicao(tipoRejeicao: String): void{this._tipoRejeicao = tipoRejeicao};
		
		public function get obs(): String{return _obs};
		public function set obs(obs: String): void{this._obs = obs}; 
		
		public function get nomeImagemStatus(): String{return _nomeImagemStatus};
		public function set nomeImagemStatus(nomeImagemStatus: String): void{this._nomeImagemStatus = nomeImagemStatus};
		
		public function get isLiberadoPDigitador(): String{return _isLiberadoPDigitador};
		public function set isLiberadoPDigitador(isLiberadoPDigitador: String): void{this._isLiberadoPDigitador = isLiberadoPDigitador};
		
		public function get idEquipamento():String
		{
			return _idEquipamento;
		}

		public function set idEquipamento(value:String):void
		{
			_idEquipamento = value;
		}

		public function get isLiberadoPOrcamentista():String
		{
			return _isLiberadoPOrcamentista;
		}

		public function set isLiberadoPOrcamentista(value:String):void
		{
			_isLiberadoPOrcamentista = value;
		}

		public function get isLiberadoPConsultor():String
		{
			return _isLiberadoPConsultor;
		}

		public function set isLiberadoPConsultor(value:String):void
		{
			_isLiberadoPConsultor = value;
		}

		public function get statusNegociacaoConsultor():String
		{
			return _statusNegociacaoConsultor;
		}

		public function set statusNegociacaoConsultor(value:String):void
		{
			_statusNegociacaoConsultor = value;
		}

		public function get obsNegociacaoConsultor():String
		{
			return _obsNegociacaoConsultor;
		}

		public function set obsNegociacaoConsultor(value:String):void
		{
			_obsNegociacaoConsultor = value;
		}

		public function get idFuncionarioConsultor():String
		{
			return _idFuncionarioConsultor;
		}

		public function set idFuncionarioConsultor(value:String):void
		{
			_idFuncionarioConsultor = value;
		}

		public function get jobControl():String
		{
			return _jobControl;
		}

		public function set jobControl(value:String):void
		{
			_jobControl = value;
		}

		public function get obsProposta():String
		{
			return _obsProposta;
		}

		public function set obsProposta(value:String):void
		{
			_obsProposta = value;
		}

		public function get backgroundColor():String
		{
			return _backgroundColor;
		}

		public function set backgroundColor(value:String):void
		{
			_backgroundColor = value;
		}

		public function get descricaoTipoFrete():String
		{
			return _descricaoTipoFrete;
		}

		public function set descricaoTipoFrete(value:String):void
		{
			_descricaoTipoFrete = value;
		}

		public function get hasDataEntregaPedidos():String
		{
			return _hasDataEntregaPedidos;
		}

		public function set hasDataEntregaPedidos(value:String):void
		{
			_hasDataEntregaPedidos = value;
		}

		public function get isOpenOs():String
		{
			return _isOpenOs;
		}

		public function set isOpenOs(value:String):void
		{
			_isOpenOs = value;
		}

		public function get isMoreThirtyDay():String
		{
			return _isMoreThirtyDay;
		}

		public function set isMoreThirtyDay(value:String):void
		{
			_isMoreThirtyDay = value;
		}

		public function get siglaPerfil():String
		{
			return _siglaPerfil;
		}

		public function set siglaPerfil(value:String):void
		{
			_siglaPerfil = value;
		}

		public function get idSegmento():Number
		{
			return _idSegmento;
		}

		public function set idSegmento(value:Number):void
		{
			_idSegmento = value;
		}

		public function get obsJobControl():String
		{
			return _obsJobControl;
		}

		public function set obsJobControl(value:String):void
		{
			_obsJobControl = value;
		}

		public function get PIPNO():String
		{
			return _PIPNO;
		}

		public function set PIPNO(value:String):void
		{
			_PIPNO = value;
		}

		public function get moMiscDesl():String
		{
			return _moMiscDesl;
		}

		public function set moMiscDesl(value:String):void
		{
			_moMiscDesl = value;
		}

		public function get valorPecas():String
		{
			return _valorPecas;
		}

		public function set valorPecas(value:String):void
		{
			_valorPecas = value;
		}

		public function get valorTotal():String
		{
			return _valorTotal;
		}

		public function set valorTotal(value:String):void
		{
			_valorTotal = value;
		}

		public function get condicaoPagamento():String
		{
			return _condicaoPagamento;
		}

		public function set condicaoPagamento(value:String):void
		{
			_condicaoPagamento = value;
		}

		public function get propNumero():String
		{
			return _propNumero;
		}

		public function set propNumero(value:String):void
		{
			_propNumero = value;
		}

		public function get autPor():String
		{
			return _autPor;
		}

		public function set autPor(value:String):void
		{
			_autPor = value;
		}

		public function get ordemCompra():String
		{
			return _ordemCompra;
		}

		public function set ordemCompra(value:String):void
		{
			_ordemCompra = value;
		}

		public function get preenchidoPor():String
		{
			return _preenchidoPor;
		}

		public function set preenchidoPor(value:String):void
		{
			_preenchidoPor = value;
		}

		public function get obsNf():String
		{
			return _obsNf;
		}

		public function set obsNf(value:String):void
		{
			_obsNf = value;
		}

		public function get ordemCompraPeca():String
		{
			return _ordemCompraPeca;
		}

		public function set ordemCompraPeca(value:String):void
		{
			_ordemCompraPeca = value;
		}

		public function get ordemCompraServico():String
		{
			return _ordemCompraServico;
		}

		public function set ordemCompraServico(value:String):void
		{
			_ordemCompraServico = value;
		}

		public function get estabelecimentoCredorPecas():String
		{
			return _estabelecimentoCredorPecas;
		}

		public function set estabelecimentoCredorPecas(value:String):void
		{
			_estabelecimentoCredorPecas = value;
		}

		public function get estabelecimentoCredorServicos():String
		{
			return _estabelecimentoCredorServicos;
		}

		public function set estabelecimentoCredorServicos(value:String):void
		{
			_estabelecimentoCredorServicos = value;
		}

		public function get condicaoPagamentoPecas():String
		{
			return _condicaoPagamentoPecas;
		}

		public function set condicaoPagamentoPecas(value:String):void
		{
			_condicaoPagamentoPecas = value;
		}

		public function get condicaoPagamentoServicos():String
		{
			return _condicaoPagamentoServicos;
		}

		public function set condicaoPagamentoServicos(value:String):void
		{
			_condicaoPagamentoServicos = value;
		}

		public function get descPorcPecas():String
		{
			return _descPorcPecas;
		}

		public function set descPorcPecas(value:String):void
		{
			_descPorcPecas = value;
		}

		public function get descPorcServicos():String
		{
			return _descPorcServicos;
		}

		public function set descPorcServicos(value:String):void
		{
			_descPorcServicos = value;
		}

		public function get descValorPecas():String
		{
			return _descValorPecas;
		}

		public function set descValorPecas(value:String):void
		{
			_descValorPecas = value;
		}

		public function get descValorServicos():String
		{
			return _descValorServicos;
		}

		public function set descValorServicos(value:String):void
		{
			_descValorServicos = value;
		}

		public function get automaticaFaturada():String
		{
			return _automaticaFaturada;
		}

		public function set automaticaFaturada(value:String):void
		{
			_automaticaFaturada = value;
		}

		public function get obsPeca():String
		{
			return _obsPeca;
		}

		public function set obsPeca(value:String):void
		{
			_obsPeca = value;
		}

		public function get obsServico():String
		{
			return _obsServico;
		}

		public function set obsServico(value:String):void
		{
			_obsServico = value;
		}

		public function get condEspecial():String
		{
			return _condEspecial;
		}

		public function set condEspecial(value:String):void
		{
			_condEspecial = value;
		}

		public function get encerrarAutomatica():String
		{
			return _encerrarAutomatica;
		}

		public function set encerrarAutomatica(value:String):void
		{
			_encerrarAutomatica = value;
		}

		public function get observacaoDesconto():String
		{
			return _observacaoDesconto;
		}

		public function set observacaoDesconto(value:String):void
		{
			_observacaoDesconto = value;
		}

		public function get isAprovadoOs():String
		{
			return _isAprovadoOs;
		}

		public function set isAprovadoOs(value:String):void
		{
			_isAprovadoOs = value;
		}

		public function get filialStr():String
		{
			return _filialStr;
		}

		public function set filialStr(value:String):void
		{
			_filialStr = value;
		}
		

		public function CheckinBean()
		{
		}
	}
}