package bean
{
	[RemoteClass(alias="com.gestaoequipamentos.bean.ComplexidadeBean")]
	public class ComplexidadeBean
	{

			private var _id:Number;
			private var _descricao:String;
			private var _fator:String;
			private var _sigla:String;
			private var _descricaoSigla:String;
			
			
			public function get id(): Number{return _id};
			public function set id(id: Number): void{this._id = id}; 
			
			public function get descricao(): String{return _descricao};
			public function set descricao(descricao: String): void{this._descricao = descricao}; 
			
			public function get fator(): String{return _fator};
			public function set fator(fator: String): void{this._fator = fator}; 
			
			public function get sigla(): String{return _sigla};
			public function set sigla(sigla: String): void{this._sigla = sigla}; 
			
			public function get descricaoSigla(): String{return _descricaoSigla};
			public function set descricaoSigla(descricaoSigla: String): void{this._descricaoSigla = descricaoSigla}; 

	
	}
}