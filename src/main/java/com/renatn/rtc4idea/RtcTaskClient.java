package com.renatn.rtc4idea;

import com.ibm.team.process.common.IProjectArea;
import com.ibm.team.repository.client.ITeamRepository;
import com.ibm.team.repository.common.IContributor;
import com.ibm.team.repository.common.TeamRepositoryException;
import com.ibm.team.workitem.client.IAuditableClient;
import com.ibm.team.workitem.client.IQueryClient;
import com.ibm.team.workitem.client.IWorkItemClient;
import com.ibm.team.workitem.common.expression.AttributeExpression;
import com.ibm.team.workitem.common.expression.Expression;
import com.ibm.team.workitem.common.expression.IQueryableAttribute;
import com.ibm.team.workitem.common.expression.IQueryableAttributeFactory;
import com.ibm.team.workitem.common.expression.QueryableAttributes;
import com.ibm.team.workitem.common.expression.Term;
import com.ibm.team.workitem.common.model.AttributeOperation;
import com.ibm.team.workitem.common.model.IWorkItem;
import com.ibm.team.workitem.common.query.IQueryResult;
import com.ibm.team.workitem.common.query.IResolvedResult;
import com.renatn.rtc4idea.tasks.RtcItem;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Renat Nasyrov (renat@gmail.com)
 * Date: 25.10.13
 * Time: 12:14
 */
public class RtcTaskClient {

    private final ITeamRepository repo;
    private final IProjectArea currentProject;
    private final IWorkItemClient workManager;

    public RtcTaskClient(ITeamRepository repo, IProjectArea currentProject) {
        this.repo = repo;
        this.currentProject = currentProject;
        this.workManager = (IWorkItemClient) repo.getClientLibrary(IWorkItemClient.class);
    }

    public RtcItem findTaskById(String id) throws TeamRepositoryException {
        IWorkItem item = workManager.findWorkItemById(Integer.valueOf(id), IWorkItem.FULL_PROFILE, null);
        return new RtcItem(item);
    }

    public List<RtcItem> findTasksAssignedToMe(int limit) throws TeamRepositoryException {

        IContributor me = repo.loggedInContributor();

        IQueryableAttributeFactory attributeFactory = QueryableAttributes.getFactory(IWorkItem.ITEM_TYPE);
        IAuditableClient auditableClient = (IAuditableClient) repo.getClientLibrary(IAuditableClient.class);

        IQueryableAttribute projectAreaAttribute = attributeFactory.findAttribute(currentProject, IWorkItem.PROJECT_AREA_PROPERTY, auditableClient, null);
        Expression projectAreaExp = new AttributeExpression(projectAreaAttribute, AttributeOperation.EQUALS, currentProject);

        IQueryableAttribute ownerAttribute = attributeFactory.findAttribute(currentProject, IWorkItem.OWNER_PROPERTY, auditableClient, null);
        Expression ownerExp = new AttributeExpression(ownerAttribute, AttributeOperation.EQUALS, me);

        Term term = new Term(Term.Operator.AND);
        term.add(projectAreaExp);
        term.add(ownerExp);

        IQueryClient queryClient = workManager.getQueryClient();
        IQueryResult<IResolvedResult<IWorkItem>> results = queryClient.getResolvedExpressionResults(currentProject, term, IWorkItem.FULL_PROFILE);

        int i = limit;
        ArrayList<RtcItem> tasks = new ArrayList<RtcItem>(limit);
        while (results.hasNext(null) && i >= 0) {
            IWorkItem item = results.next(null).getItem();
            tasks.add(new RtcItem(item));
            i--;
        }

        return tasks;
    }

}
