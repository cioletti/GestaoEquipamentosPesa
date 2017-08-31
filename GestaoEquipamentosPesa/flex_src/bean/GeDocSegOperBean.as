package bean
{

	[RemoteClass(alias="com.gestaoequipamentos.bean.GeDocSegOperBean")]
	public class GeDocSegOperBean
	{
		private var _id:Number;
		private var _idSegmento:Number;
		private var _idOperacao:Number;
		private var _numDoc:String;
		private var _msgDbs:String;
		private var _dataCriacao:String;
		private var _descOperacao:String;
		private var _descSegmento:String;
		private var _status:String;
		private var _statusUrlImage:String;
		
		private var _codErroDbs:String;
		private var _msgDbsRemocao:String;
		private var _nomeFuncionario:String;
		private var _idFuncionario:String;
		private var _descricao:String;
		
		
		public function get id():Number{return _id};
		public function set id(id:Number):void{this._id = id}; 
		
		public function get idSegmento(): Number{return _idSegmento};
		public function set idSegmento(idSegmento: Number): void{this._idSegmento = idSegmento}; 
		
		public function get idOperacao(): Number{return _idOperacao};
		public function set idOperacao(idOperacao: Number): void{this._idOperacao = idOperacao};
		
		public function get numDoc(): String{return _numDoc};
		public function set numDoc(numDoc: String): void{this._numDoc = numDoc}; 
		
		public function get msgDbs(): String{return _msgDbs};
		public function set msgDbs(msgDbs: String): void{this._msgDbs = msgDbs}; 
		
		public function get dataCriacao(): String{return _dataCriacao};
		public function set dataCriacao(dataCriacao: String): void{this._dataCriacao = dataCriacao};
		
		public function get descOperacao(): String{return _descOperacao};
		public function set descOperacao(descOperacao: String): void{this._descOperacao = descOperacao};
		
		public function get descSegmento(): String{return _descSegmento};
		public function set descSegmento(descSegmento: String): void{this._descSegmento = descSegmento};
		
		public function get status(): String{return _status};
		public function set status(status: String): void{this._status = status};
		
		public function get statusUrlImage(): String{return _statusUrlImage};
		public function set statusUrlImage(statusUrlImage: String): void{this._statusUrlImage = statusUrlImage}
		public function get codErroDbs():String
		{
			return _codErroDbs;
		}

		public function set codErroDbs(value:String):void
		{
			_codErroDbs = value;
		}

		public function get msgDbsRemocao():String
		{
			return _msgDbsRemocao;
		}

		public function set msgDbsRemocao(value:String):void
		{
			_msgDbsRemocao = value;
		}

		public function get nomeFuncionario():String
		{
			return _nomeFuncionario;
		}

		public function set nomeFuncionario(value:String):void
		{
			_nomeFuncionario = value;
		}

		public function get descricao():String
		{
			return _descricao;
		}

		public function set descricao(value:String):void
		{
			_descricao = value;
		}

		public function get idFuncionario():String
		{
			return _idFuncionario;
		}

		public function set idFuncionario(value:String):void
		{
			_idFuncionario = value;
		}


;
		
		
		public function GeDocSegOperBean()
		{
		}
	}
}