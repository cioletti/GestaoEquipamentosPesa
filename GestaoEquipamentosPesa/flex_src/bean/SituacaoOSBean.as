package bean
{
	[RemoteClass(alias="com.gestaoequipamentos.bean.SituacaoOSBean")]
	public class SituacaoOSBean
	{
		private var _id:Number;
		private var _numeroOS:String;
		private var _dataChegada:String;
		private var _estimateByDataChegada:String;
		private var _dataEntregaPedidos:String;
		private var _estimateByDataEntregaPedidos:String;
		private var _dataOrcamento:String;
		private var _estimateByDataOrcamento:String;
		private var _dataAprovacao:String;
		private var _estimateByDataAprovacao:String;
		private var _dataPrevisaoEntrega:String;
		private var _estimateByDataPrevisaoEntrega:String;
		private var _dataTerminoServico:String;
		private var _estimateByDataTerminoServico:String;
		private var _dataFaturamento:String;
		private var _estimateByDataFaturamento:String;
		private var _filial:Number;
		private var _idCheckin:String;
		private var _isCheckout:String;
		private var _tipoRejeicao:String;
		private var _obs:String;
		private var _statusOs:String;
				
		
		public function SituacaoOSBean()
		{
		}

		public function get statusOs():String
		{
			return _statusOs;
		}

		public function set statusOs(value:String):void
		{
			_statusOs = value;
		}

		public function get id():Number
		{
			return _id;
		}

		public function set id(value:Number):void
		{
			_id = value;
		}

		public function get numeroOS():String
		{
			return _numeroOS;
		}

		public function set numeroOS(value:String):void
		{
			_numeroOS = value;
		}

		public function get dataChegada():String
		{
			return _dataChegada;
		}

		public function set dataChegada(value:String):void
		{
			_dataChegada = value;
		}

		public function get dataEntregaPedidos():String
		{
			return _dataEntregaPedidos;
		}

		public function set dataEntregaPedidos(value:String):void
		{
			_dataEntregaPedidos = value;
		}

		public function get dataOrcamento():String
		{
			return _dataOrcamento;
		}

		public function set dataOrcamento(value:String):void
		{
			_dataOrcamento = value;
		}

		public function get dataAprovacao():String
		{
			return _dataAprovacao;
		}

		public function set dataAprovacao(value:String):void
		{
			_dataAprovacao = value;
		}

		public function get dataPrevisaoEntrega():String
		{
			return _dataPrevisaoEntrega;
		}

		public function set dataPrevisaoEntrega(value:String):void
		{
			_dataPrevisaoEntrega = value;
		}

		public function get dataTerminoServico():String
		{
			return _dataTerminoServico;
		}

		public function set dataTerminoServico(value:String):void
		{
			_dataTerminoServico = value;
		}

		public function get dataFaturamento():String
		{
			return _dataFaturamento;
		}

		public function set dataFaturamento(value:String):void
		{
			_dataFaturamento = value;
		}

		public function get filial():Number
		{
			return _filial;
		}

		public function set filial(value:Number):void
		{
			_filial = value;
		}

		public function get idCheckin():String
		{
			return _idCheckin;
		}

		public function set idCheckin(value:String):void
		{
			_idCheckin = value;
		}

		public function get isCheckout():String
		{
			return _isCheckout;
		}

		public function set isCheckout(value:String):void
		{
			_isCheckout = value;
		}

		public function get tipoRejeicao():String
		{
			return _tipoRejeicao;
		}

		public function set tipoRejeicao(value:String):void
		{
			_tipoRejeicao = value;
		}

		public function get obs():String
		{
			return _obs;
		}

		public function set obs(value:String):void
		{
			_obs = value;
		}

		public function get estimateByDataChegada():String
		{
			return _estimateByDataChegada;
		}

		public function set estimateByDataChegada(value:String):void
		{
			_estimateByDataChegada = value;
		}

		public function get estimateByDataEntregaPedidos():String
		{
			return _estimateByDataEntregaPedidos;
		}

		public function set estimateByDataEntregaPedidos(value:String):void
		{
			_estimateByDataEntregaPedidos = value;
		}

		public function get estimateByDataOrcamento():String
		{
			return _estimateByDataOrcamento;
		}

		public function set estimateByDataOrcamento(value:String):void
		{
			_estimateByDataOrcamento = value;
		}

		public function get estimateByDataAprovacao():String
		{
			return _estimateByDataAprovacao;
		}

		public function set estimateByDataAprovacao(value:String):void
		{
			_estimateByDataAprovacao = value;
		}

		public function get estimateByDataPrevisaoEntrega():String
		{
			return _estimateByDataPrevisaoEntrega;
		}

		public function set estimateByDataPrevisaoEntrega(value:String):void
		{
			_estimateByDataPrevisaoEntrega = value;
		}

		public function get estimateByDataTerminoServico():String
		{
			return _estimateByDataTerminoServico;
		}

		public function set estimateByDataTerminoServico(value:String):void
		{
			_estimateByDataTerminoServico = value;
		}

		public function get estimateByDataFaturamento():String
		{
			return _estimateByDataFaturamento;
		}

		public function set estimateByDataFaturamento(value:String):void
		{
			_estimateByDataFaturamento = value;
		}


	}
}