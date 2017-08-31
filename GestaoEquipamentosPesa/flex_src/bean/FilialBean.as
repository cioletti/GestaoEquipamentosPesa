package bean
{
	[RemoteClass(alias="com.gestaoequipamentos.bean.FilialBean")]
	public class FilialBean
	{
		private var _stno: Number;
		private var _stnm: String;
		private var _sigla: String;
		
		public function FilialBean(){}
		
		public function get sigla():String
		{
			return _sigla;
		}

		public function set sigla(value:String):void
		{
			_sigla = value;
		}

		public function get stno(): Number{return _stno};
		public function set stno(stno: Number): void{this._stno = stno}; 
		
		public function get stnm(): String{return _stnm};
		public function set stnm(stnm: String): void{this._stnm = stnm}; 
	}
}