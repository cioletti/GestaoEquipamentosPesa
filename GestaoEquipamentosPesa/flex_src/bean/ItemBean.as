package bean
{
	[RemoteClass(alias="com.gestaoequipamentos.bean.ItemBean")]
	public class ItemBean
	{
		private var _id:Number;
		private var _descricao:String;
		private var _codigo:String;
		private var _valor:String;
		private var _qtd:Number;
		
		public function get id():Number
		{
			return _id;
		}

		public function set id(value:Number):void
		{
			_id = value;
		}

		public function get descricao():String
		{
			return _descricao;
		}

		public function set descricao(value:String):void
		{
			_descricao = value;
		}

		public function get codigo():String
		{
			return _codigo;
		}

		public function set codigo(value:String):void
		{
			_codigo = value;
		}

		public function get valor():String
		{
			return _valor;
		}

		public function set valor(value:String):void
		{
			_valor = value;
		}

		public function get qtd():Number
		{
			return _qtd;
		}

		public function set qtd(value:Number):void
		{
			_qtd = value;
		}

		
	}
}