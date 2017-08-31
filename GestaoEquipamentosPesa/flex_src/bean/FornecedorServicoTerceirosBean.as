package bean
{
	[RemoteClass(alias="com.gestaoequipamentos.bean.FornecedorServicoTerceirosBean")]
	public class FornecedorServicoTerceirosBean
	{
		private var _id:Number;
		private var _descricao:String;
		private var _endereco:String;
		private var _telefone:String;
		private var _email:String;
		private var _cnpj:String;
		private var _codigo:String;
		
		public function FornecedorServicoTerceirosBean()
		{
		}

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

		public function get endereco():String
		{
			return _endereco;
		}

		public function set endereco(value:String):void
		{
			_endereco = value;
		}

		public function get telefone():String
		{
			return _telefone;
		}

		public function set telefone(value:String):void
		{
			_telefone = value;
		}

		public function get email():String
		{
			return _email;
		}

		public function set email(value:String):void
		{
			_email = value;
		}

		public function get cnpj():String
		{
			return _cnpj;
		}

		public function set cnpj(value:String):void
		{
			_cnpj = value;
		}

		public function get codigo():String
		{
			return _codigo;
		}

		public function set codigo(value:String):void
		{
			_codigo = value;
		}


	}
}