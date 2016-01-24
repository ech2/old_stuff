package net.wintermuse.owl;

import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.query.*;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.RepositoryResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by oscii on 05/08/14.
 */
public class OwlModel {
    private Repository repo;

    public OwlModel(Repository repo) {
        if (repo == null) {
            throw new NullPointerException("Can't create OwlModel with null repo.");
        }
        this.repo = repo;
    }

    // Repository querying

    public TupleQueryResult queryTuple(String queryString) {
        TupleQueryResult result = null;
        try {
            RepositoryConnection con = repo.getConnection();
            try {
                TupleQuery tupleQuery =con.prepareTupleQuery(
                        QueryLanguage.SPARQL, queryString);
                result = tupleQuery.evaluate();
            } catch (MalformedQueryException | QueryEvaluationException e) {
                e.printStackTrace();
            } finally {
                con.close();
            }
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        return result;
    }

    public GraphQueryResult queryGraph(String queryString) {
        GraphQueryResult result = null;
        try {
            RepositoryConnection con = repo.getConnection();
            try {
                GraphQuery graphQuery = con.prepareGraphQuery(
                        QueryLanguage.SPARQL, queryString);
                result = graphQuery.evaluate();
            } catch (MalformedQueryException | QueryEvaluationException e) {
                e.printStackTrace();
            } finally {
                con.close();
            }
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void queryUpdate(String queryString) {
        try {
            RepositoryConnection con = repo.getConnection();
            try {
                Update updateQuery = con.prepareUpdate(
                        QueryLanguage.SPARQL, queryString);
                updateQuery.execute();
            } catch (MalformedQueryException | UpdateExecutionException e) {
                e.printStackTrace();
            }
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }

    public void addStatement(Statement statement) {
        try {
            RepositoryConnection con = repo.getConnection();
            try {
                con.add(statement);
            } catch (RepositoryException e) {
                e.printStackTrace();
            } finally {
                con.close();
            }
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }

    public void addStatements(Collection<Statement> statements) {
        try {
            RepositoryConnection con = repo.getConnection();
            try {
                con.add(statements);
            } catch (RepositoryException e) {
                e.printStackTrace();
            } finally {
                con.close();
            }
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }

    public List<Statement> getStatements(Resource subj, URI pred, Value obj) {
        List<Statement> result = new ArrayList<>();
        try {
            RepositoryConnection con = repo.getConnection();
            try {
                RepositoryResult<Statement> r = con.getStatements(subj, pred, obj, false);
                while (r.hasNext()) {
                    result.add(r.next());
                }
            } catch (RepositoryException e) {
                e.printStackTrace();
            } finally {
                con.close();
            }
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        return result;
    }

}
