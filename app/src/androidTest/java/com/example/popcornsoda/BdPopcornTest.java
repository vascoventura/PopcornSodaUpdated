package com.example.popcornsoda;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;
import com.example.popcornsoda.BdPopcorn.BdPopcornOpenHelper;
import com.example.popcornsoda.BdPopcorn.BdTableAutores;
import com.example.popcornsoda.BdPopcorn.BdTableFilmes;
import com.example.popcornsoda.BdPopcorn.BdTableSeries;
import com.example.popcornsoda.models.Autor;
import com.example.popcornsoda.models.Movie;
import com.example.popcornsoda.models.Serie;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


@RunWith(AndroidJUnit4.class)
public class BdPopcornTest {
    @Before
    public void apagaBaseDados() {
        getAppContext().deleteDatabase(BdPopcornOpenHelper.NOME_BASE_DADOS);
    }

    @Test
    public void criaBdPopcorn() {
        // Context of the app under test.
        Context appContext = getAppContext();

        BdPopcornOpenHelper openHelper = new BdPopcornOpenHelper(appContext);

        SQLiteDatabase db = openHelper.getReadableDatabase();

        assertTrue(db.isOpen());
    }

    @SuppressWarnings("deprecation")
    private Context getAppContext() {
        return InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void testCRUD() {
        BdPopcornOpenHelper openHelper = new BdPopcornOpenHelper(getAppContext());
        SQLiteDatabase db = openHelper.getWritableDatabase();

        BdTableAutores tabelaAutores = new BdTableAutores(db);

        // Teste read autores (cRud)
        Cursor cursorAutores = getAutores(tabelaAutores);
        assertEquals(0, cursorAutores.getCount());

        // Teste create/read categorias (CRud)

        String nome = "Ruben Fleischer";
        int ano = 1974;
        String nacionalidade = "EUA";
        long idRubenFleischer = criaAutor(tabelaAutores, nome, ano, nacionalidade);

        cursorAutores = getAutores(tabelaAutores);
        assertEquals(1, cursorAutores.getCount());

        Autor autor = getAutorComID(cursorAutores, idRubenFleischer);

        assertEquals(nome, autor.getNome_autor());
        assertEquals(ano, autor.getAno_nascimento());
        assertEquals(nacionalidade, autor.getNacionalidade());


        //NOVO
        nome = "Peter Farrelly";
        ano = 1956;
        nacionalidade = "EUA";
        long idPeterFarelly = criaAutor(tabelaAutores, nome, ano, nacionalidade);

        cursorAutores = getAutores(tabelaAutores);
        assertEquals(2, cursorAutores.getCount());

        autor = getAutorComID(cursorAutores, idPeterFarelly);

        assertEquals(nome, autor.getNome_autor());
        assertEquals(ano, autor.getAno_nascimento());
        assertEquals(nacionalidade, autor.getNacionalidade());

        // Teste Update/Read categorias (cRUd)

        //UPDATE
        nome = "Ruben Fleischer";
        ano = 1974;
        nacionalidade = "EUA";

        autor.setNome_autor(nome);
        autor.setAno_nascimento(ano);
        autor.setNacionalidade(nacionalidade);

        int registosAlterados = tabelaAutores.update(autor.getContentValues(), BdTableAutores._ID + "=?", new String[]{String.valueOf(idRubenFleischer)});

        assertEquals(1, registosAlterados);

        cursorAutores = getAutores(tabelaAutores);
        autor = getAutorComID(cursorAutores, idRubenFleischer);

        assertEquals(nome, autor.getNome_autor());
        assertEquals(ano, autor.getAno_nascimento());
        assertEquals(nacionalidade, autor.getNacionalidade());

        // Teste Create/Delete/Read autores (CRuD)
        long id = criaAutor(tabelaAutores, "M. Night Shyamalan", 1970, "India");
        cursorAutores = getAutores(tabelaAutores);
        assertEquals(3, cursorAutores.getCount());

        tabelaAutores.delete(BdTableAutores._ID + "=?", new String[]{String.valueOf(id)});
        cursorAutores = getAutores(tabelaAutores);
        assertEquals(2, cursorAutores.getCount());

        getAutorComID(cursorAutores, idRubenFleischer);
        getAutorComID(cursorAutores, idPeterFarelly);


        //todo:checkpoint

        //TESTES FILMES

        BdTableFilmes tabelaFilmes = new BdTableFilmes(db);

        // Teste read filmes (cRud)
        Cursor cursorFilmes = getFilmes(tabelaFilmes);
        assertEquals(0, cursorFilmes.getCount());

        // Teste create/read filmes (CRud)
        nome = "Venom";
        String tipo = "Suspense";
        double classificacao = 6.7;
        ano = 2018;
        String descricao = "Eddie Brock é um jornalista que investiga o misterioso trabalho de um cientista, suspeito de utilizar cobaias humanas em experimentos mortais. Quando ele acaba entrando em contato com um simbionte alienígena, Eddie se torna Venom, uma máquina de matar incontrolável, que nem ele pode conter";


        id = criaFilme(tabelaFilmes,nome, tipo, idRubenFleischer, classificacao, ano,descricao);
        cursorFilmes = getFilmes(tabelaFilmes);
        assertEquals(1, cursorFilmes.getCount());



        Movie movie = getFilmeComID(cursorFilmes, id);

        assertEquals(nome,  movie.getNome_filme());
        assertEquals(tipo,  movie.getTipo_filme());
        assertEquals(idRubenFleischer,  movie.getAutor_filme());
        assertEquals(classificacao,  movie.getClassificacao_filme(), 1);
        assertEquals(ano,  movie.getAno_filme());
        assertEquals(descricao,  movie.getDescricao_filme());


        nome = "The Upside";
        tipo = "Drama";
        id = criaFilme(tabelaFilmes, nome, tipo, idPeterFarelly, classificacao, ano, descricao);
        classificacao = 6.7;
        ano = 2018;
        descricao = "Eddie Brock é um jornalista que investiga o misterioso trabalho de um cientista, suspeito de utilizar cobaias humanas em experimentos mortais. Quando ele acaba entrando em contato com um simbionte alienígena, Eddie se torna Venom, uma máquina de matar incontrolável, que nem ele pode conter";
        cursorFilmes = getFilmes(tabelaFilmes);
        assertEquals(2, cursorFilmes.getCount());


        movie = getFilmeComID(cursorFilmes, id);
        assertEquals(nome,  movie.getNome_filme());
        assertEquals(tipo,  movie.getTipo_filme());
        assertEquals(idPeterFarelly,  movie.getAutor_filme());
        assertEquals(classificacao,  movie.getClassificacao_filme(), 1);
        assertEquals(ano,  movie.getAno_filme());
        assertEquals(descricao,  movie.getDescricao_filme());


        id = criaFilme(tabelaFilmes, "Star Wars IV - A new hope", "Aventura", idPeterFarelly, 8.6, 1974, "Luke Skywalker (Mark Hammil) sonha ir para a Academia como seus amigos, mas se vê envolvido em uma guerra intergalática quando seu tio compra dois robôs e com eles encontra uma mensagem da princesa Leia Organa (Carrie Fisher) para o jedi Obi-Wan Kenobi (Alec Guiness) sobre os planos da construção da Estrela da Morte, uma gigantesca estação espacial com capacidade para destruir um planeta.");
        cursorFilmes = getFilmes((tabelaFilmes));
        assertEquals(3, cursorFilmes.getCount());

        // Teste read/update livros (cRUd)

        movie = getFilmeComID(cursorFilmes, id);
        nome = "Glass";
        tipo = "Drama";
        classificacao = 6.8;
        ano = 2019;
        descricao = "Após a conclusão de Fragmentado (2017), Kevin Crumb (James McAvoy), o homem com 23 personalidades diferentes, passa a ser perseguido por David Dunn (Bruce Willis), o herói de Corpo Fechado (2000). O jogo de gato e rato entre o homem inquebrável e a Fera é influenciado pela presença de Elijah Price (Samuel L. Jackson), que manipula os encontros entre eles e mantém segredos sobre os dois.";


        movie.setNome_filme(nome);
        movie.setTipo_filme(tipo);
        movie.setAutor_filme(idRubenFleischer);
        movie.setClassificacao_filme(classificacao);
        movie.setAno_filme(ano);
        movie.setDescricao_filme(descricao);

        tabelaFilmes.update(movie.getContentValues(), BdTableFilmes._ID + "=?", new String[]{String.valueOf(id)});

        cursorFilmes = getFilmes(tabelaFilmes);

        movie = getFilmeComID(cursorFilmes, id);
        assertEquals(nome,  movie.getNome_filme());
        assertEquals(tipo,  movie.getTipo_filme());
        assertEquals(idRubenFleischer,  movie.getAutor_filme());
        assertEquals(classificacao,  movie.getClassificacao_filme(), 1);
        assertEquals(ano,  movie.getAno_filme());
        assertEquals(descricao,  movie.getDescricao_filme());


        // Teste read/delete filmes (cRuD)
        tabelaFilmes.delete(BdTableFilmes._ID + "=?", new String[]{String.valueOf(id)});
        cursorFilmes = getFilmes(tabelaFilmes);
        assertEquals(2, cursorFilmes.getCount());

        //todo:checkpoint


       //TESTES SERIES

        BdTableSeries tabelaSeries = new BdTableSeries(db);

        // Teste read series (cRud)
        Cursor cursorSeries = getSeries(tabelaSeries);
        assertEquals(0, cursorSeries.getCount());

        // Teste create/read filmes (CRud)
        nome = "How I Met Your Mother";
        tipo = "Comédia";
        classificacao = 8.3;
        ano = 2005;
        int temporadas = 9;
        descricao = "A comédia enfoca a busca de Ted para encontrar o amor de sua vida. Tudo começa quando seu melhor amigo, Marshall, anuncia seu noivado com a namorada Lily, uma professora do jardim da infância. Neste instante, Ted se dá conta de que precisa fazer alguma coisa para não terminar sozinho. Para ajudá-lo em sua busca está Barney, um amigo com opiniões infindáveis e sugestões muitas vezes imprudentes. Quando Ted conhece Robin, ele acredita ser amor à primeira vista.";

        id = criaSerie(tabelaSeries, nome,tipo, idRubenFleischer, classificacao, ano, temporadas, descricao);
        cursorSeries = getSeries(tabelaSeries);
        assertEquals(1, cursorSeries.getCount());


        Serie serie = getSerieComID(cursorSeries, id);
        assertEquals(nome,  serie.getNome_serie());
        assertEquals(tipo,  serie.getTipo_serie());
        assertEquals(idRubenFleischer,  serie.getAutor_serie());
        assertEquals(classificacao,  serie.getClassificacao_serie(), 1);
        assertEquals(ano,  serie.getAno_serie());
        assertEquals(temporadas, serie.getTemporadas());
        assertEquals(descricao,  serie.getDescricao_serie());


        nome = "How I Met Your Mother";
        tipo = "Comédia";
        id = criaFilme(tabelaFilmes, nome, tipo, idRubenFleischer, classificacao, ano, descricao);
        classificacao = 8.9;
        ano = 2005;
        descricao = "A comédia enfoca a busca de Ted para encontrar o amor de sua vida. Tudo começa quando seu melhor amigo, Marshall, anuncia seu noivado com a namorada Lily, uma professora do jardim da infância. Neste instante, Ted se dá conta de que precisa fazer alguma coisa para não terminar sozinho. Para ajudá-lo em sua busca está Barney, um amigo com opiniões infindáveis e sugestões muitas vezes imprudentes. Quando Ted conhece Robin, ele acredita ser amor à primeira vista.";
        cursorSeries = getSeries(tabelaSeries);
        assertEquals(1, cursorSeries.getCount());


        serie = getSerieComID(cursorSeries, id);
        assertEquals(nome,  serie.getNome_serie());
        assertEquals(tipo,  serie.getTipo_serie());
        assertEquals(idRubenFleischer,  serie.getAutor_serie());
        assertEquals(classificacao,  serie.getClassificacao_serie(), 1);
        assertEquals(ano,  serie.getAno_serie());
        assertEquals(temporadas, serie.getTemporadas());
        assertEquals(descricao,  serie.getDescricao_serie());


        id = criaSerie(tabelaSeries, "Prison Break",  "Crime", idPeterFarelly, 8.4, 2005, 5,"Após a prisão de Lincoln Burrows (Dominic Purcell), condenado por um crime que não cometeu, o engenheiro Michael Scofield (Wentworth Miller) bola um plano para tirar o irmão da cadeia. Enviado para Fox River ao lado de Lincoln, Michael começa a executar a sua estratégia, mas logo percebe que está no meio de uma perigosa conspiração. Para garantir a liberdade da sua família, ele precisará enganar a Dra. Sara Tancredi (Sarah Wayne Callies) e se associar a criminosos condenados.");
        cursorSeries = getSeries((tabelaSeries));
        assertEquals(2, cursorSeries.getCount());

        // Teste read/update livros (cRUd)

        serie = getSerieComID(cursorSeries, id);
        nome = "Game of Thrones";
        tipo = "Aventura";
        classificacao = 9.4;
        ano = 2011;
        temporadas = 8;
        descricao = "A série passa-se em Westeros, uma terra reminiscente da Europa Medieval, onde as estações duram por anos ou até mesmo décadas. A história gira em torno de uma batalha entre os Sete Reinos, onde duas famílias dominantes lutam pelo controlo do Trono de Ferro, cuja posse possivelmente assegurará a sobrevivência durante o inverno que está por vir.";


        serie.setNome_serie(nome);
        serie.setTipo_serie(tipo);
        serie.setAutor_serie(idRubenFleischer);
        serie.setClassificacao_serie(classificacao);
        serie.setAno_serie(ano);
        serie.setImagem_fundo_serie(temporadas);
        serie.setDescricao_serie(descricao);

        tabelaSeries.update(serie.getContentValues(), BdTableSeries._ID + "=?", new String[]{String.valueOf(id)});

        cursorSeries = getSeries(tabelaSeries);

        serie = getSerieComID(cursorSeries, id);
        assertEquals(nome,  serie.getNome_serie());
        assertEquals(tipo,  serie.getTipo_serie());
        assertEquals(idRubenFleischer,  serie.getAutor_serie());
        assertEquals(classificacao,  serie.getClassificacao_serie(), 1);
        assertEquals(ano,  serie.getAno_serie());
        assertEquals(descricao,  serie.getDescricao_serie());


        // Teste read/delete serie (cRuD)
        tabelaSeries.delete(BdTableSeries._ID + "=?", new String[]{String.valueOf(id)});
        cursorSeries = getSeries(tabelaSeries);
        assertEquals(1, cursorSeries.getCount());


    }

    private Serie getSerieComID(Cursor cursor, long id) {
        Serie serie = null;

        while (cursor.moveToNext()) {
            serie = Serie.fromCursor(cursor);

            if (serie.getId_serie() == id) {
                break;
            }
        }
        assertNotNull(serie);

        return serie;
    }

    private long criaSerie(BdTableSeries tabelaSeries, String nome, String tipo, long autor, double classificacao, int ano, int temporadas, String descricao) {

        Serie serie = new Serie();

        serie.setNome_serie(nome);
        serie.setTipo_serie(tipo);
        serie.setAutor_serie(autor);
        serie.setClassificacao_serie(classificacao);
        serie.setAno_serie(ano);
        serie.setTemporadas(temporadas);
        serie.setDescricao_serie(descricao);

        long id = tabelaSeries.insert(serie.getContentValues());
        assertNotEquals(-1, id);

        return id;
    }


    private Cursor getSeries(BdTableSeries tabelaSeries) {
        return tabelaSeries.query(BdTableSeries.TODAS_COLUNAS, null, null, null, null, null);
    }


    private long criaAutor(BdTableAutores tabelaAutores, String nome, int ano_nascimento, String nacionalidade) {

        Autor autor = new Autor();
            autor.setNome_autor(nome);
            autor.setAno_nascimento(ano_nascimento);
            autor.setNacionalidade(nacionalidade);

            long id = tabelaAutores.insert(autor.getContentValues());
            assertNotEquals(-1, id);

            return id;
        }

        private Cursor getAutores(BdTableAutores tabelaAutores) {
            return tabelaAutores.query(BdTableAutores.TODAS_COLUNAS, null, null, null, null, null);
        }

        private Autor getAutorComID(Cursor cursor, long id) {

            Autor autor = null;

            while (cursor.moveToNext()) {
                autor = Autor.fromCursor(cursor);

                if (autor.getId() == id) {
                    break;
                }
            }

            assertNotNull(autor);

            return autor;
        }

        private long criaFilme(BdTableFilmes tabelaFilmes, String nome, String tipo, long autor, double classificacao, int ano, String descricao) {

            Movie movie = new Movie();

            movie.setNome_filme(nome);
            movie.setTipo_filme(tipo);
            movie.setAutor_filme(autor);
            movie.setClassificacao_filme(classificacao);
            movie.setAno_filme(ano);
            movie.setDescricao_filme(descricao);

            long id = tabelaFilmes.insert(movie.getContentValues());
            assertNotEquals(-1, id);

            return id;
        }

        private Cursor getFilmes(BdTableFilmes tabelaFilmes) {
            return tabelaFilmes.query(BdTableFilmes.TODAS_COLUNAS, null, null, null, null, null);
        }

        private Movie getFilmeComID(Cursor cursor, long id) {
            Movie movie = null;

            while (cursor.moveToNext()) {
                movie = Movie.fromCursor(cursor);

                if (movie.getId_filme() == id) {
                    break;
                }
            }

            assertNotNull(movie);

            return movie;
        }
}

