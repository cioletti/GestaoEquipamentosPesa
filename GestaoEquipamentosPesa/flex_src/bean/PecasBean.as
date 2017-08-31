package bean
{
	import mx.collections.ArrayCollection;

	[RemoteClass(alias="com.gestaoequipamentos.bean.PecasBean")]
	public class PecasBean
	{
		private var _partNo:String;
		private var _partName:String;
		private var _id:String;
		private var _qtd:String;
		private var _groupNumber:String;
		private var _referenceNo:String;
		private var _smcsCode:String;
		private var _groupName:String;
		private var _userId:String;
		private var _serialNo:String;
		private var _idDocSegOper:Number;
		private var _sos:String;
		private var _pecasList:ArrayCollection;
		private var _coderr:String;
		private var _descerr:String;
		private var _nomeFuncionario:String;
		private var _numDoc:String;
		
		public function get partNo(): String{return _partNo};
		public function set partNo(partNo: String): void{this._partNo = partNo}; 
		
		public function get partName(): String{return _partName};
		public function set partName(partName: String): void{this._partName = partName}; 
		
		public function get id(): String{return _id};
		public function set id(id: String): void{this._id = id};
		
		public function get qtd(): String{return _qtd};
		public function set qtd(qtd: String): void{this._qtd = qtd}; 
		
		public function get groupNumber(): String{return _groupNumber};
		public function set groupNumber(groupNumber: String): void{this._groupNumber = groupNumber}; 
		
		public function get referenceNo(): String{return _referenceNo};
		public function set referenceNo(referenceNo: String): void{this._referenceNo = referenceNo};
		
		public function get smcsCode(): String{return _smcsCode};
		public function set smcsCode(smcsCode: String): void{this._smcsCode = smcsCode};
		
		public function get groupName(): String{return _groupName};
		public function set groupName(groupName: String): void{this._groupName = groupName};
		
		public function get userId(): String{return _userId};
		public function set userId(userId: String): void{this._userId = userId};
		
		public function get serialNo(): String{return _serialNo};
		public function set serialNo(serialNo: String): void{this._serialNo = serialNo};
		
		public function get idDocSegOper(): Number{return _idDocSegOper};
		public function set idDocSegOper(idDocSegOper: Number): void{this._idDocSegOper = idDocSegOper};
		
		public function get sos(): String{return _sos};
		public function set sos(sos: String): void{this._sos = sos};
		
		public function get pecasList(): ArrayCollection{return _pecasList};
		public function set pecasList(pecasList: ArrayCollection): void{this._pecasList = pecasList}
		public function get coderr():String
		{
			return _coderr;
		}

		public function set coderr(value:String):void
		{
			_coderr = value;
		}

		public function get descerr():String
		{
			return _descerr;
		}

		public function set descerr(value:String):void
		{
			_descerr = value;
		}

		public function get nomeFuncionario():String
		{
			return _nomeFuncionario;
		}

		public function set nomeFuncionario(value:String):void
		{
			_nomeFuncionario = value;
		}

		public function get numDoc():String
		{
			return _numDoc;
		}

		public function set numDoc(value:String):void
		{
			_numDoc = value;
		}


;
		
		
		
		
		public function PecasBean()
		{
		}
	}
}