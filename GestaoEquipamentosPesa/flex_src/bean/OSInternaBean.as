package bean
{
	[RemoteClass(alias="com.gestaoequipamentos.bean.OSInternaBean")]
	public class OSInternaBean
	{
		private var _idModelo:Number;
		private var _descricaoModelo:String;
		private var _idfamilia:Number;
		private var _serie:String;
		private var _horimetro:String;
		private var _telefone:String;
		private var _contato:String;
		private var _nomeCliente:String;
		private var _emailContato:String;

		
		public function get idModelo(): Number{return _idModelo};
		public function set idModelo(idModelo: Number): void{this._idModelo = idModelo}; 
		
		public function get idfamilia(): Number{return _idfamilia};
		public function set idfamilia(idfamilia: Number): void{this._idfamilia = idfamilia}; 
		
		public function get serie(): String{return _serie};
		public function set serie(serie: String): void{this._serie = serie}; 
		
		public function get horimetro(): String{return _horimetro};
		public function set horimetro(horimetro: String): void{this._horimetro = horimetro}; 
		
		public function get telefone(): String{return _telefone};
		public function set telefone(telefone: String): void{this._telefone = telefone}; 
		
		public function get descricaoModelo(): String{return _descricaoModelo};
		public function set descricaoModelo(descricaoModelo: String): void{this._descricaoModelo = descricaoModelo}; 
		
		public function get contato(): String{return _contato};
		public function set contato(contato: String): void{this._contato = contato};
		
		public function get nomeCliente(): String{return _nomeCliente};
		public function set nomeCliente(nomeCliente: String): void{this._nomeCliente = nomeCliente}
		public function get emailContato():String
		{
			return _emailContato;
		}

		public function set emailContato(value:String):void
		{
			_emailContato = value;
		}

; 

	}
}