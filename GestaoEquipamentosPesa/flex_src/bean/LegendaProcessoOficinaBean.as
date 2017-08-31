package bean
{
	[RemoteClass(alias="com.gestaoequipamentos.bean.LegendaProcessoOficinaBean")]
	public class LegendaProcessoOficinaBean
	{
		private var _id:Number;
		private var _descricao:String;
		private var _sigla:String;
		private var _ordem:Number;
		
		public function get id(): Number{return _id};
		public function set id(id: Number): void{this._id = id}; 
		
		public function get descricao(): String{return _descricao};
		public function set descricao(descricao: String): void{this._descricao = descricao}
		public function get sigla():String
		{
			return _sigla;
		}

		public function set sigla(value:String):void
		{
			_sigla = value;
		}

		public function get ordem():Number
		{
			return _ordem;
		}

		public function set ordem(value:Number):void
		{
			_ordem = value;
		}

 
	}
}