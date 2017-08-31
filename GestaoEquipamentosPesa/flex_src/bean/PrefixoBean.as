package bean
{
	[RemoteClass(alias="com.gestaoequipamentos.bean.PrefixoBean")]
	public class PrefixoBean
	{
		private var _id:Number;
		private var _descricao:String;
		private var _idModelo:Number;
		private var _valor:String;
		private var _valorCusto:String;
		private var _sigla:String;
		
		
		public function get valor():String
		{
			return _valor;
		}

		public function set valor(value:String):void
		{
			_valor = value;
		}
		public function get valorCusto():String
		{
			return _valorCusto;
		}

		public function set valorCusto(value:String):void
		{
			_valorCusto = value;
		}

		public function get sigla():String
		{
			return _sigla;
		}

		public function set sigla(value:String):void
		{
			_sigla = value;
		}

		public function get idModelo():Number
		{
			return _idModelo;
		}

		public function set idModelo(value:Number):void
		{
			_idModelo = value;
		}

		public function get id(): Number{return _id};
		public function set id(id: Number): void{this._id = id}; 
		
		public function get descricao(): String{return _descricao};
		public function set descricao(descricao: String): void{this._descricao = descricao}; 
		
	}
}