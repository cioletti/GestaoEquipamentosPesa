package bean
{
	import mx.collections.ArrayCollection;

	[RemoteClass(alias="com.gestaoequipamentos.bean.PecasAprovacaoOSBean")]
	public class PecasAprovacaoOSBean
	{
		private var _id:String;
		private var _partNo:String;
		private var _partName:String;
		private var _qtd:String;
		private var _uniteRate:String;
		private var _dnPrice:String;
		private var _uniteCl:String;
		private var _clPrice:String;
		
		public function get id(): String{return _id};
		public function set id(id: String): void{this._id = id};
		
		public function get partNo(): String{return _partNo};
		public function set partNo(partNo: String): void{this._partNo = partNo}; 
		
		public function get partName(): String{return _partName};
		public function set partName(partName: String): void{this._partName = partName}; 
		
		public function get qtd(): String{return _qtd};
		public function set qtd(qtd: String): void{this._qtd = qtd}; 
		
		
		
		
		
		public function PecasAprovacaoOSBean()
		{
		}

		public function get uniteRate():String
		{
			return _uniteRate;
		}

		public function set uniteRate(value:String):void
		{
			_uniteRate = value;
		}

		public function get dnPrice():String
		{
			return _dnPrice;
		}

		public function set dnPrice(value:String):void
		{
			_dnPrice = value;
		}

		public function get uniteCl():String
		{
			return _uniteCl;
		}

		public function set uniteCl(value:String):void
		{
			_uniteCl = value;
		}

		public function get clPrice():String
		{
			return _clPrice;
		}

		public function set clPrice(value:String):void
		{
			_clPrice = value;
		}


	}
}