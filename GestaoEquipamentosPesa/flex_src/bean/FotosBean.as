package bean
{
	[RemoteClass(alias="com.gestaoequipamentos.bean.FotosBean")]
	public class FotosBean
	{	
		
			private var _id: Number;
			private var _numeroOs:String;
			private var _observacao:String;
			private var _jobControl:String;
			private var _idCheckin: Number;
			private var _nomeArquivo: String;
			private var _tituloFotos:String;
			private var _descricaoFalhaFotos:String;
			private var _conclusaoFotos:String;

			
			public function get id(): Number{return _id};
			public function set id(id: Number): void{this._id = id}
			public function get numeroOs():String
			{
				return _numeroOs;
			}

			public function set numeroOs(value:String):void
			{
				_numeroOs = value;
			}

			public function get observacao():String
			{
				return _observacao;
			}

			public function set observacao(value:String):void
			{
				_observacao = value;
			}

			public function get jobControl():String
			{
				return _jobControl;
			}

			public function set jobControl(value:String):void
			{
				_jobControl = value;
			}

			public function get idCheckin():Number
			{
				return _idCheckin;
			}

			public function set idCheckin(value:Number):void
			{
				_idCheckin = value;
			}

			public function get nomeArquivo():String
			{
				return _nomeArquivo;
			}

			public function set nomeArquivo(value:String):void
			{
				_nomeArquivo = value;
			}

			public function get tituloFotos():String
			{
				return _tituloFotos;
			}

			public function set tituloFotos(value:String):void
			{
				_tituloFotos = value;
			}

			public function get descricaoFalhaFotos():String
			{
				return _descricaoFalhaFotos;
			}

			public function set descricaoFalhaFotos(value:String):void
			{
				_descricaoFalhaFotos = value;
			}

			public function get conclusaoFotos():String
			{
				return _conclusaoFotos;
			}

			public function set conclusaoFotos(value:String):void
			{
				_conclusaoFotos = value;
			}

			
	}
	}
