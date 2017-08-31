package bean
{
	import mx.collections.ArrayCollection;
		
	[RemoteClass(alias="com.gestaoequipamentos.bean.OperacaoBean")]
	public class OperacaoBean
	{

			private var _idSegmento:String;
			private var _id:Number;
			private var _numero:String;
			private var _jbcd: String;
			private var _jbcdStr: String;
			private var _desricao: String;
			private var _cptcd:String;
			private var _descricaoImportPecas:String;
			private var _idFuncionarioCriador:String;
			private var _codErroDbs:String;
			private var _msgErroDbs:String;
			
			
			public function get idSegmento():String{return _idSegmento};
			public function set idSegmento(idSegmento:String):void{this._idSegmento = idSegmento};
			
			public function get id():Number{return _id};
			public function set id(id:Number):void{this._id = id};
			
			public function get numero():String{return _numero};
			public function set numero(numero:String):void{this._numero = numero};
			
			public function get jbcd():String{return _jbcd};
			public function set jbcd(jbcd:String):void{this._jbcd = jbcd}; 
			
			public function get jbcdStr():String{return _jbcdStr};
			public function set jbcdStr(jbcdStr:String):void{this._jbcdStr = jbcdStr}; 
			
			public function get desricao():String{return _desricao};
			public function set desricao(desricao:String):void{this._desricao = desricao}; 
			
			public function get cptcd():String{return _cptcd};
			public function set cptcd(cptcd:String):void{this._cptcd= cptcd};

			public function get descricaoImportPecas():String{return _descricaoImportPecas};
			public function set descricaoImportPecas(descricaoImportPecas:String):void{this._descricaoImportPecas= descricaoImportPecas};
			
			public function get idFuncionarioCriador():String{return _idFuncionarioCriador};
			public function set idFuncionarioCriador(idFuncionarioCriador:String):void{this._idFuncionarioCriador= idFuncionarioCriador}
			public function get msgErroDbs():String
			{
				return _msgErroDbs;
			}

			public function set msgErroDbs(value:String):void
			{
				_msgErroDbs = value;
			}

			public function get codErroDbs():String
			{
				return _codErroDbs;
			}

			public function set codErroDbs(value:String):void
			{
				_codErroDbs = value;
			}


;

			
			public function OperacaoBean()
			{
				
			}
			
			
		}
	}
