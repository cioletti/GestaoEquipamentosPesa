package bean
{
	[RemoteClass(alias="com.gestaoequipamentos.bean.FatorReajusteBean")]
	public class FatorReajusteBean
	{
		private var _id:Number;
		private var _data:String;
		private var _fatorAjuste:String;
		public function FatorReajusteBean()
		{
		}

		public function get fatorAjuste():String
		{
			return _fatorAjuste;
		}

		public function set fatorAjuste(value:String):void
		{
			_fatorAjuste = value;
		}

		public function get data():String
		{
			return _data;
		}

		public function set data(value:String):void
		{
			_data = value;
		}

		public function get id():Number
		{
			return _id;
		}

		public function set id(value:Number):void
		{
			_id = value;
		}

	}
}