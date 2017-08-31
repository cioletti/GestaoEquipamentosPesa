package bean
{
	[RemoteClass(alias="com.gestaoequipamentos.bean.ContatoBean")]
	public class ContatoBean
	{	
		
			private var _contato: String;
			private var _telefone: String;
			
			public function BibliografiaBean(){}
			
			public function get contato(): String{return _contato};
			public function set contato(contato: String): void{this._contato = contato}; 
			
			public function get telefone(): String{return _telefone};
			public function set telefone(telefone: String): void{this._telefone = telefone}; 
		}
	}
