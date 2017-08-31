package bean
{
	import mx.collections.ArrayCollection;

	[RemoteClass(alias="com.gestaoequipamentos.bean.PecasBoTreeBean")]
	public class PecasBoTreeBean
	{
		
		private var _children:ArrayCollection;

		// Campos 1º nível da árvore
		private var _rfdcno:String;
		private var _wono:String;
		private var _wosgno:String;
		private var _woopno:String;
		
		// Campos 2º nível da árvore
		private var _pano20:String;
		private var _ds18:String;
		private var _qybo:String;
		private var _qyor:String;
		private var _data:String;
		private var _statusReal:String;		
		
		public function PecasBoTreeBean()
		{
		}
	
		public function get children():ArrayCollection
		{
			return _children;
		}
		
		public function set children(value:ArrayCollection):void
		{
			_children = value;
		}		
		
		public function get rfdcno():String
		{
			return _rfdcno;
		}

		public function set rfdcno(value:String):void
		{
			_rfdcno = value;
		}

		public function get wono():String
		{
			return _wono;
		}

		public function set wono(value:String):void
		{
			_wono = value;
		}

		public function get wosgno():String
		{
			return _wosgno;
		}

		public function set wosgno(value:String):void
		{
			_wosgno = value;
		}

		public function get woopno():String
		{
			return _woopno;
		}

		public function set woopno(value:String):void
		{
			_woopno = value;
		}

		public function get pano20():String
		{
			return _pano20;
		}

		public function set pano20(value:String):void
		{
			_pano20 = value;
		}

		public function get ds18():String
		{
			return _ds18;
		}

		public function set ds18(value:String):void
		{
			_ds18 = value;
		}

		public function get qybo():String
		{
			return _qybo;
		}

		public function set qybo(value:String):void
		{
			_qybo = value;
		}

		public function get qyor():String
		{
			return _qyor;
		}

		public function set qyor(value:String):void
		{
			_qyor = value;
		}

		public function get data():String
		{
			return _data;
		}

		public function set data(value:String):void
		{
			_data = value;
		}

		public function get statusReal():String
		{
			return _statusReal;
		}

		public function set statusReal(value:String):void
		{
			_statusReal = value;
		}
	}
}