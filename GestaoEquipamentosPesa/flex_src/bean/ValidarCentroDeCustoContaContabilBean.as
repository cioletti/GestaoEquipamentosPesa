package bean
{
	[RemoteClass(alias="com.gestaoequipamentos.bean.ValidarCentroDeCustoContaContabilBean")]
	public class ValidarCentroDeCustoContaContabilBean
	{
		private var _clienteInter:String;
		private var _centroDeCusto:String;
		private var _centroDeCustoSigla:String;
		private var _contaContabil:String;
		private var _contaContabilSigla:String;
		private var _indGarantia:String;
		private var _tipoCliente:String;
		private var _urgencia:String;
		private var _modeloMaquina:String;
		private var _filial:Number;
		private var _codigoLiberacao:String;
		
		public function get filial():Number
		{
			return _filial;
		}

		public function set filial(value:Number):void
		{
			_filial = value;
		}

		public function get clienteInter(): String{return _clienteInter};
		public function set clienteInter(clienteInter: String): void{this._clienteInter = clienteInter}; 
		
		public function get centroDeCusto(): String{return _centroDeCusto};
		public function set centroDeCusto(centroDeCusto: String): void{this._centroDeCusto = centroDeCusto}; 
		
		public function get contaContabil(): String{return _contaContabil};
		public function set contaContabil(contaContabil: String): void{this._contaContabil = contaContabil}; 
		
		public function get indGarantia(): String{return _indGarantia};
		public function set indGarantia(indGarantia: String): void{this._indGarantia = indGarantia}; 
		
		public function get tipoCliente(): String{return _tipoCliente};
		public function set tipoCliente(tipoCliente: String): void{this._tipoCliente = tipoCliente}; 

		public function get centroDeCustoSigla(): String{return _centroDeCustoSigla};
		public function set centroDeCustoSigla(centroDeCustoSigla: String): void{this._centroDeCustoSigla = centroDeCustoSigla}; 

		public function get contaContabilSigla(): String{return _contaContabilSigla};
		public function set contaContabilSigla(contaContabilSigla: String): void{this._contaContabilSigla = contaContabilSigla}; 

		public function get urgencia(): String{return _urgencia};
		public function set urgencia(urgencia: String): void{this._urgencia = urgencia}; 
		
		public function get modeloMaquina(): String{return _modeloMaquina};
		public function set modeloMaquina(modeloMaquina: String): void{this._modeloMaquina = modeloMaquina}
		public function get codigoLiberacao():String
		{
			return _codigoLiberacao;
		}

		public function set codigoLiberacao(value:String):void
		{
			_codigoLiberacao = value;
		}

; 
		
		public function ValidarCentroDeCustoContaContabilBean()
		{
		}
	}
}