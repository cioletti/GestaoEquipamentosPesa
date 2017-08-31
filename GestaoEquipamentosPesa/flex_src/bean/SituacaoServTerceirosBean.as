package bean
{
	import mx.utils.NameUtil;

	[RemoteClass(alias="com.gestaoequipamentos.bean.SituacaoServTerceirosBean")]
	public class SituacaoServTerceirosBean
	{
		private var _id:Number;
		private var _descricao:String;
		private var _dataEnvioMetrologia:String;
		private var _idFuncEnvioMetrologia:String;
		private var _obsEnvioMetrologia:String;
		private var _dataEnvioRecepcao:String;
		private var _idFuncEnvioRecepcao:String;
		private var _obsEnvioRecepcao:String;
		private var _dataEnvioFornecedor:String;
		private var _idFuncEnvioFornecedor:String;
		private var _obsEnvioFornecedor:String;
		private var _dataFinalizadoFornecedor:String;
		private var _idFuncFinalizadoFornecedor:String;
		private var _obsFinalizadoFornecedor:String;
		private var _dataEntradaEnvioMetrologia:String;
		private var _idFuncEntradaEnvioMetrologia:String;
		private var _obsEntradaEnvioMetrologia:String;
		private var _dataAprovacaoMetrologia:String;
		private var _idFuncAprovacaoMetrologia:String;
		private var _obsAprovacaoMetrologia:String;
		private var _dataRejeicaoMetrologia:String;
		private var _idFuncRejeicaoMetrologia:String;
		private var _obsRejeicaoMetrologia:String;
		private var _dataEnvioNotaFiscas:String;
		private var _idFuncEnvioNotaFiscas:String;
		private var _obsEnvioNotaFiscas:String;
		
		public function SituacaoServTerceirosBean()
		{
		}

		public function get id():Number
		{
			return _id;
		}

		public function set id(value:Number):void
		{
			_id = value;
		}

		public function get dataEnvioMetrologia():String
		{
			return _dataEnvioMetrologia;
		}

		public function set dataEnvioMetrologia(value:String):void
		{
			_dataEnvioMetrologia = value;
		}

		public function get idFuncEnvioMetrologia():String
		{
			return _idFuncEnvioMetrologia;
		}

		public function set idFuncEnvioMetrologia(value:String):void
		{
			_idFuncEnvioMetrologia = value;
		}

		public function get obsEnvioMetrologia():String
		{
			return _obsEnvioMetrologia;
		}

		public function set obsEnvioMetrologia(value:String):void
		{
			_obsEnvioMetrologia = value;
		}

		public function get dataEnvioRecepcao():String
		{
			return _dataEnvioRecepcao;
		}

		public function set dataEnvioRecepcao(value:String):void
		{
			_dataEnvioRecepcao = value;
		}

		public function get idFuncEnvioRecepcao():String
		{
			return _idFuncEnvioRecepcao;
		}

		public function set idFuncEnvioRecepcao(value:String):void
		{
			_idFuncEnvioRecepcao = value;
		}

		public function get obsEnvioRecepcao():String
		{
			return _obsEnvioRecepcao;
		}

		public function set obsEnvioRecepcao(value:String):void
		{
			_obsEnvioRecepcao = value;
		}

		public function get dataEnvioFornecedor():String
		{
			return _dataEnvioFornecedor;
		}

		public function set dataEnvioFornecedor(value:String):void
		{
			_dataEnvioFornecedor = value;
		}

		public function get idFuncEnvioFornecedor():String
		{
			return _idFuncEnvioFornecedor;
		}

		public function set idFuncEnvioFornecedor(value:String):void
		{
			_idFuncEnvioFornecedor = value;
		}

		public function get obsEnvioFornecedor():String
		{
			return _obsEnvioFornecedor;
		}

		public function set obsEnvioFornecedor(value:String):void
		{
			_obsEnvioFornecedor = value;
		}

		public function get dataFinalizadoFornecedor():String
		{
			return _dataFinalizadoFornecedor;
		}

		public function set dataFinalizadoFornecedor(value:String):void
		{
			_dataFinalizadoFornecedor = value;
		}

		public function get idFuncFinalizadoFornecedor():String
		{
			return _idFuncFinalizadoFornecedor;
		}

		public function set idFuncFinalizadoFornecedor(value:String):void
		{
			_idFuncFinalizadoFornecedor = value;
		}

		public function get obsFinalizadoFornecedor():String
		{
			return _obsFinalizadoFornecedor;
		}

		public function set obsFinalizadoFornecedor(value:String):void
		{
			_obsFinalizadoFornecedor = value;
		}

		public function get dataEntradaEnvioMetrologia():String
		{
			return _dataEntradaEnvioMetrologia;
		}

		public function set dataEntradaEnvioMetrologia(value:String):void
		{
			_dataEntradaEnvioMetrologia = value;
		}

		public function get idFuncEntradaEnvioMetrologia():String
		{
			return _idFuncEntradaEnvioMetrologia;
		}

		public function set idFuncEntradaEnvioMetrologia(value:String):void
		{
			_idFuncEntradaEnvioMetrologia = value;
		}

		public function get obsEntradaEnvioMetrologia():String
		{
			return _obsEntradaEnvioMetrologia;
		}

		public function set obsEntradaEnvioMetrologia(value:String):void
		{
			_obsEntradaEnvioMetrologia = value;
		}

		public function get dataAprovacaoMetrologia():String
		{
			return _dataAprovacaoMetrologia;
		}

		public function set dataAprovacaoMetrologia(value:String):void
		{
			_dataAprovacaoMetrologia = value;
		}

		public function get idFuncAprovacaoMetrologia():String
		{
			return _idFuncAprovacaoMetrologia;
		}

		public function set idFuncAprovacaoMetrologia(value:String):void
		{
			_idFuncAprovacaoMetrologia = value;
		}

		public function get obsAprovacaoMetrologia():String
		{
			return _obsAprovacaoMetrologia;
		}

		public function set obsAprovacaoMetrologia(value:String):void
		{
			_obsAprovacaoMetrologia = value;
		}

		public function get dataRejeicaoMetrologia():String
		{
			return _dataRejeicaoMetrologia;
		}

		public function set dataRejeicaoMetrologia(value:String):void
		{
			_dataRejeicaoMetrologia = value;
		}

		public function get idFuncRejeicaoMetrologia():String
		{
			return _idFuncRejeicaoMetrologia;
		}

		public function set idFuncRejeicaoMetrologia(value:String):void
		{
			_idFuncRejeicaoMetrologia = value;
		}

		public function get obsRejeicaoMetrologia():String
		{
			return _obsRejeicaoMetrologia;
		}

		public function set obsRejeicaoMetrologia(value:String):void
		{
			_obsRejeicaoMetrologia = value;
		}

		public function get descricao():String
		{
			return _descricao;
		}

		public function set descricao(value:String):void
		{
			_descricao = value;
		}

		public function get dataEnvioNotaFiscas():String
		{
			return _dataEnvioNotaFiscas;
		}

		public function set dataEnvioNotaFiscas(value:String):void
		{
			_dataEnvioNotaFiscas = value;
		}

		public function get idFuncEnvioNotaFiscas():String
		{
			return _idFuncEnvioNotaFiscas;
		}

		public function set idFuncEnvioNotaFiscas(value:String):void
		{
			_idFuncEnvioNotaFiscas = value;
		}

		public function get obsEnvioNotaFiscas():String
		{
			return _obsEnvioNotaFiscas;
		}

		public function set obsEnvioNotaFiscas(value:String):void
		{
			_obsEnvioNotaFiscas = value;
		}


	}
}