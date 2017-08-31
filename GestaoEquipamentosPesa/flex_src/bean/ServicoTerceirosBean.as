package bean
{
	import mx.collections.ArrayCollection;

	[RemoteClass(alias="com.gestaoequipamentos.bean.ServicoTerceirosBean")]
	public class ServicoTerceirosBean
	{
		private var _id:Number;
		private var _idSegmento:Number;
		private var _idTipoServicoTerceiros:Number;
		private var _numeroSegmento:String;
		private var _jobControl:String;
		private var _numeroOs:String;
		private var _valor:String;
		private var _descricao:String;
		private var _qtdDiasProposta:String;
		private var _qtdServTerceiros:Number;
		private var _obs:String;
		private var _idStatusServTerceiros:Number;
		private var _statusServTerceiros:String;
		private var _siglaStatusServTerceiros:String;
		private var _descricaoFornecedor:String;
		private var _idFornecedor:Number;
		private var _fornecedorList:ArrayCollection;
		private var _idNaturezaOperacao:Number;
		private var _itemList:ArrayCollection;
		
		private var _codigoFornecedor:String;
		private var _enviadoPor:String;
		private var _dataAbertura:String;
		private var _nomeFilial:String;
		private var _cnpj:String;
		private var _naturezaOperacao:String;
		private var _arquivoDetalhe:String;
		private var _localServico:String;
		private var _descricaoLocalServico:String;
		private var _modelo:String;
		private var _serie:String;
		private var _obsEnvioNotaFiscal:String;
		private var _urlStatus:String;
		private var _statusServico:String;
		private var _garantia:String;
		private var _statusNegociacao:String;
		private var _statusNegociacaoConsultor:String;
		private var _dataPrevisaoEntrega:String;
		
		public function ServicoTerceirosBean()
		{
		}

		public function get garantia():String
		{
			return _garantia;
		}

		public function set garantia(value:String):void
		{
			_garantia = value;
		}

		public function get statusServico():String
		{
			return _statusServico;
		}

		public function set statusServico(value:String):void
		{
			_statusServico = value;
		}

		public function get urlStatus():String
		{
			return _urlStatus;
		}

		public function set urlStatus(value:String):void
		{
			_urlStatus = value;
		}

		public function get obs():String
		{
			return _obs;
		}

		public function set obs(value:String):void
		{
			_obs = value;
		}

		public function get descricao():String
		{
			return _descricao;
		}

		public function set descricao(value:String):void
		{
			_descricao = value;
		}

		public function get valor():String
		{
			return _valor;
		}

		public function set valor(value:String):void
		{
			_valor = value;
		}

		public function get idTipoServicoTerceiros():Number
		{
			return _idTipoServicoTerceiros;
		}

		public function set idTipoServicoTerceiros(value:Number):void
		{
			_idTipoServicoTerceiros = value;
		}

		public function get idSegmento():Number
		{
			return _idSegmento;
		}

		public function set idSegmento(value:Number):void
		{
			_idSegmento = value;
		}
		public function get qtdDiasProposta():String
		{
			return _qtdDiasProposta;
		}

		public function set qtdDiasProposta(value:String):void
		{
			_qtdDiasProposta = value;
		}

		public function get qtdServTerceiros():Number
		{
			return _qtdServTerceiros;
		}

		public function set qtdServTerceiros(value:Number):void
		{
			_qtdServTerceiros = value;
		}

		public function get idStatusServTerceiros():Number
		{
			return _idStatusServTerceiros;
		}

		public function set idStatusServTerceiros(value:Number):void
		{
			_idStatusServTerceiros = value;
		}

		public function get statusServTerceiros():String
		{
			return _statusServTerceiros;
		}

		public function set statusServTerceiros(value:String):void
		{
			_statusServTerceiros = value;
		}

		public function get siglaStatusServTerceiros():String
		{
			return _siglaStatusServTerceiros;
		}

		public function set siglaStatusServTerceiros(value:String):void
		{
			_siglaStatusServTerceiros = value;
		}

		public function get descricaoFornecedor():String
		{
			return _descricaoFornecedor;
		}

		public function set descricaoFornecedor(value:String):void
		{
			_descricaoFornecedor = value;
		}

		public function get idFornecedor():Number
		{
			return _idFornecedor;
		}

		public function set idFornecedor(value:Number):void
		{
			_idFornecedor = value;
		}

		public function get numeroSegmento():String
		{
			return _numeroSegmento;
		}

		public function set numeroSegmento(value:String):void
		{
			_numeroSegmento = value;
		}

		public function get jobControl():String
		{
			return _jobControl;
		}

		public function set jobControl(value:String):void
		{
			_jobControl = value;
		}

		public function get numeroOs():String
		{
			return _numeroOs;
		}

		public function set numeroOs(value:String):void
		{
			_numeroOs = value;
		}

		public function get fornecedorList():ArrayCollection
		{
			return _fornecedorList;
		}

		public function set fornecedorList(value:ArrayCollection):void
		{
			_fornecedorList = value;
		}

		public function get idNaturezaOperacao():Number
		{
			return _idNaturezaOperacao;
		}

		public function set idNaturezaOperacao(value:Number):void
		{
			_idNaturezaOperacao = value;
		}

		public function get itemList():ArrayCollection
		{
			return _itemList;
		}

		public function set itemList(value:ArrayCollection):void
		{
			_itemList = value;
		}


		public function get codigoFornecedor():String
		{
			return _codigoFornecedor;
		}

		public function set codigoFornecedor(value:String):void
		{
			_codigoFornecedor = value;
		}

		public function get enviadoPor():String
		{
			return _enviadoPor;
		}

		public function set enviadoPor(value:String):void
		{
			_enviadoPor = value;
		}

		public function get dataAbertura():String
		{
			return _dataAbertura;
		}

		public function set dataAbertura(value:String):void
		{
			_dataAbertura = value;
		}

		public function get id():Number
		{
			return _id;
		}

		public function set id(value:Number):void
		{
			_id = value;
		}

		public function get nomeFilial():String
		{
			return _nomeFilial;
		}

		public function set nomeFilial(value:String):void
		{
			_nomeFilial = value;
		}

		public function get cnpj():String
		{
			return _cnpj;
		}

		public function set cnpj(value:String):void
		{
			_cnpj = value;
		}

		public function get naturezaOperacao():String
		{
			return _naturezaOperacao;
		}

		public function set naturezaOperacao(value:String):void
		{
			_naturezaOperacao = value;
		}

		public function get arquivoDetalhe():String
		{
			return _arquivoDetalhe;
		}

		public function set arquivoDetalhe(value:String):void
		{
			_arquivoDetalhe = value;
		}

		public function get localServico():String
		{
			return _localServico;
		}

		public function set localServico(value:String):void
		{
			_localServico = value;
		}

		public function get descricaoLocalServico():String
		{
			return _descricaoLocalServico;
		}

		public function set descricaoLocalServico(value:String):void
		{
			_descricaoLocalServico = value;
		}

		public function get modelo():String
		{
			return _modelo;
		}

		public function set modelo(value:String):void
		{
			_modelo = value;
		}

		public function get serie():String
		{
			return _serie;
		}

		public function set serie(value:String):void
		{
			_serie = value;
		}

		public function get obsEnvioNotaFiscal():String
		{
			return _obsEnvioNotaFiscal;
		}

		public function set obsEnvioNotaFiscal(value:String):void
		{
			_obsEnvioNotaFiscal = value;
		}

		public function get statusNegociacao():String
		{
			return _statusNegociacao;
		}

		public function set statusNegociacao(value:String):void
		{
			_statusNegociacao = value;
		}

		public function get statusNegociacaoConsultor():String
		{
			return _statusNegociacaoConsultor;
		}

		public function set statusNegociacaoConsultor(value:String):void
		{
			_statusNegociacaoConsultor = value;
		}

		public function get dataPrevisaoEntrega():String
		{
			return _dataPrevisaoEntrega;
		}

		public function set dataPrevisaoEntrega(value:String):void
		{
			_dataPrevisaoEntrega = value;
		}


	}
}