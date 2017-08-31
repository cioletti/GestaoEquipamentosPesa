package bean
{
	import mx.collections.ArrayCollection;

	[RemoteClass(alias="com.gestaoequipamentos.bean.DetalhesPropostaBean")]
	public class DetalhesPropostaBean
	{
		private var _id:Number;
		private var _segmentoList:ArrayCollection;
		private var _formaEntregaProposta:String;
		private var _aosCuidados:String;
		private var _telefone:String;
		private var _email:String;
		private var _objetoProposta:String;
		private var _maquina:String;
		private var _modelo:String;
		private var _serie:String;
		private var _observacao:String;
		private var _condicaoPagamento:String;
		private var _prazoEntrega:Number;
		private var _validadeProposta:String;
		private var _aprovacaoPropostaServico:String;
		private var _orcamentista:String;
		private var _imprimirSubTributaria:String;
		private var _isFindSubTributaria:String;
		private var _fatorUrgencia:String;
		public function DetalhesPropostaBean()
		{
		}

		public function get fatorUrgencia():String
		{
			return _fatorUrgencia;
		}

		public function set fatorUrgencia(value:String):void
		{
			_fatorUrgencia = value;
		}

		public function get isFindSubTributaria():String
		{
			return _isFindSubTributaria;
		}

		public function set isFindSubTributaria(value:String):void
		{
			_isFindSubTributaria = value;
		}

		public function get imprimirSubTributaria():String
		{
			return _imprimirSubTributaria;
		}

		public function set imprimirSubTributaria(value:String):void
		{
			_imprimirSubTributaria = value;
		}

		public function get id():Number
		{
			return _id;
		}

		public function set id(value:Number):void
		{
			_id = value;
		}

		public function get observacao():String
		{
			return _observacao;
		}

		public function set observacao(value:String):void
		{
			_observacao = value;
		}

		public function get orcamentista():String
		{
			return _orcamentista;
		}

		public function set orcamentista(value:String):void
		{
			_orcamentista = value;
		}

		public function get aprovacaoPropostaServico():String
		{
			return _aprovacaoPropostaServico;
		}

		public function set aprovacaoPropostaServico(value:String):void
		{
			_aprovacaoPropostaServico = value;
		}

		public function get validadeProposta():String
		{
			return _validadeProposta;
		}

		public function set validadeProposta(value:String):void
		{
			_validadeProposta = value;
		}

		public function get prazoEntrega():Number
		{
			return _prazoEntrega;
		}

		public function set prazoEntrega(value:Number):void
		{
			_prazoEntrega = value;
		}

		public function get condicaoPagamento():String
		{
			return _condicaoPagamento;
		}

		public function set condicaoPagamento(value:String):void
		{
			_condicaoPagamento = value;
		}


		public function get serie():String
		{
			return _serie;
		}

		public function set serie(value:String):void
		{
			_serie = value;
		}

		public function get modelo():String
		{
			return _modelo;
		}

		public function set modelo(value:String):void
		{
			_modelo = value;
		}

		public function get maquina():String
		{
			return _maquina;
		}

		public function set maquina(value:String):void
		{
			_maquina = value;
		}

		public function get objetoProposta():String
		{
			return _objetoProposta;
		}

		public function set objetoProposta(value:String):void
		{
			_objetoProposta = value;
		}

		public function get email():String
		{
			return _email;
		}

		public function set email(value:String):void
		{
			_email = value;
		}

		public function get telefone():String
		{
			return _telefone;
		}

		public function set telefone(value:String):void
		{
			_telefone = value;
		}

		public function get aosCuidados():String
		{
			return _aosCuidados;
		}

		public function set aosCuidados(value:String):void
		{
			_aosCuidados = value;
		}

		public function get formaEntregaProposta():String
		{
			return _formaEntregaProposta;
		}

		public function set formaEntregaProposta(value:String):void
		{
			_formaEntregaProposta = value;
		}

		public function get segmentoList():ArrayCollection
		{
			return _segmentoList;
		}

		public function set segmentoList(value:ArrayCollection):void
		{
			_segmentoList = value;
		}

	}
}