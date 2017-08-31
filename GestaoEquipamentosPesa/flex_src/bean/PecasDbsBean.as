package bean
{
	[RemoteClass(alias="com.gestaoequipamentos.bean.PecasDbsBean")]
	public class PecasDbsBean
	{
		
		private var _codigo:String;
		private var _qtd:Long;
		private var _numPeca:String;
		private var _nomePeca:String;
		private var _sos:String;
		
		public function get codigo(): String{return _codigo};
		public function set codigo(codigo: String): void{this._codigo = codigo}; 
		
		public function get qtd(): String{return _qtd};
		public function set qtd(qtd: String): void{this._qtd = qtd}; 
		
		public function get numPeca(): String{return _numPeca};
		public function set numPeca(numPeca: String): void{this._numPeca = numPeca};
		
		public function get nomePeca(): String{return _nomePeca};
		public function set nomePeca(nomePeca: String): void{this._nomePeca = nomePeca};
		
		public function get sos(): String{return _sos};
		public function set sos(sos: String): void{this._sos = sos}; 		
		
		}
	}