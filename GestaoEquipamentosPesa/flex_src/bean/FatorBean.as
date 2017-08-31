package bean
{
	[RemoteClass(alias="com.gestaoequipamentos.bean.FatorBean")]
	public class FatorBean
	{
		
		private var _id:Number;
		private var _valorInter:String;
		private var _fatorUrgente:String;
		
		public function get id():Number{return _id};
		public function set id(id:Number):void{this._id = id}; 
	
		
		public function get valorInter():String{return _valorInter};
		public function set valorInter(valorInter:String):void{this._valorInter = valorInter}; 
		
		public function get fatorUrgente():String{return _fatorUrgente};
		public function set fatorUrgente(fatorUrgente:String):void{this._fatorUrgente = fatorUrgente}; 
		
		public function FatorBean()
		{
		}
	}
}