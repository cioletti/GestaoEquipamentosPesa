package bean
{
	[RemoteClass(alias="com.gestaoequipamentos.bean.DataHeaderBean")]
	public class DataHeaderBean
	{
		
//		private var _dateOpen:String;
//		private var _cliente:String;
//		private var _numOs:String;
//		private var _descricao:String;
//		private var _modelo:String;
//		private var _prazoPETS:String;
//		private var _filial:String;

		private var _dateString:String;
		private var _descricao:String;
		private var _data:Date;
		
		public function get dateString(): String{return _dateString};
		public function set dateString(dateString: String): void{this._dateString = dateString}; 

		public function get descricao(): String{return _descricao};
		public function set descricao(descricao: String): void{this._descricao = descricao}; 
		
		public function get data(): Date{return _data};
		public function set data(data: Date): void{this._data = data}; 
		
		public function DataHeaderBean()
		{
		}
	}
}