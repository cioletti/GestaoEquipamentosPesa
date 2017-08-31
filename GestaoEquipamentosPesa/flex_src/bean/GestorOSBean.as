package bean
{
	[RemoteClass(alias="com.gestaoequipamentos.bean.GestorOSBean")]
	public class GestorOSBean
	{
		private var _numOS:String;
		private var _modelo:String;
		private var _cliente:String;
		private var _serie:String;
		private var _filial:String;
		private var _dataNegociacao:String;
		private var _dataAprovacao:String;
		private var _codCliente:String;
		private var _idCheckin:String;
		
		public function get numOS(): String{return _numOS};
		public function set numOS(numOS: String): void{this._numOS = numOS}; 

		public function get modelo(): String{return _modelo};
		public function set modelo(modelo: String): void{this._modelo = modelo};
		
		public function get cliente(): String{return _cliente};
		public function set cliente(cliente: String): void{this._cliente = cliente}; 
		
		public function get serie(): String{return _serie};
		public function set serie(serie: String): void{this._serie = serie}; 
		
		public function get filial(): String{return _filial};
		public function set filial(filial: String): void{this._filial = filial}; 
		
		public function get dataNegociacao(): String{return _dataNegociacao};
		public function set dataNegociacao(dataNegociacao: String): void{this._dataNegociacao = dataNegociacao};
		
		public function get dataAprovacao(): String{return _dataAprovacao};
		public function set dataAprovacao(dataAprovacao: String): void{this._dataAprovacao = dataAprovacao}; 
		
		public function get codCliente(): String{return _codCliente};
		public function set codCliente(codCliente: String): void{this._codCliente = codCliente}; 
		
		public function get idCheckin(): String{return _idCheckin};
		public function set idCheckin(idCheckin: String): void{this._idCheckin = idCheckin}; 
		
		public function GestorOSBean()
		{
		}
	}
}