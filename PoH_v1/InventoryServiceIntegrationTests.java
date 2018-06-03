package service.inventory;

import model.inventory.Operation;
import model.inventory.Portfolio;
import model.inventory.Program;
import model.inventory.Project;
import model.inventory.enums.RecursionType;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Test;
import util.HibernateUtil;
import util.exception.InvalidParentComponentException;


import static org.junit.Assert.*;

/**
 * Created by Wojciech on 2015-06-25.
 */
public class InventoryServiceIntegrationTests {
    @After
    public void clearDataFromDatabase() {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx =session.beginTransaction();



            session.createQuery("delete from AreaOfFocus").executeUpdate();
            session.createQuery("delete from CategoryMembership ").executeUpdate();
            session.createQuery("delete from CategoryEvaluation ").executeUpdate();
            session.createQuery("delete from Score ").executeUpdate();

            session.createQuery("delete from Category ").executeUpdate();
            session.createQuery("delete from ScoringCriterion ").executeUpdate();

            session.createQuery("delete from Component").executeUpdate();
            session.createQuery("delete from Portfolio").executeUpdate();
            session.createQuery("delete from Program").executeUpdate();
            session.createQuery("delete from Project ").executeUpdate();
            session.createQuery("delete from Operation ").executeUpdate();

            session.createQuery("delete from State ").executeUpdate();
            session.createQuery("delete from Process ").executeUpdate();

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    //COMPONENT
    @Test(expected=InvalidParentComponentException.class)
    public void componentWithParentComponentShouldBeForbidenToUpdate() throws InvalidParentComponentException {
        InventoryServiceInterface inventoryService = new InventoryService();
        Portfolio portfolio = inventoryService.createPortfolio("PF1", "GrassHost", "customer jakis", "Opis Opisik", null);
        portfolio.setCode("PFFF");
        portfolio.setName("NOWY GrasshosT");
        portfolio.setParent(portfolio);
    }
    //PORTFOLIO
    @Test
    public void aNewPortfolioShouldBeCreated(){
        Portfolio portfolio = new InventoryService().createPortfolio("PF1", "GrassHost", "customer jakis", "Opis Opisik", null);
        assertEquals("PF1", portfolio.getCode());
        assertEquals("GrassHost", portfolio.getName());
        assertEquals("Opis Opisik", portfolio.getDescription());
        assertNull(portfolio.getParent());
        assertNotEquals(portfolio.getId(), 0);
    }
    @Test
    public void portfolioShouldBeTakenById() {
        InventoryServiceInterface inventoryService = new InventoryService();
        Portfolio portfolio = inventoryService.createPortfolio("PF1", "GrassHost", "customer jakis", "Opis Opisik", null);
        Portfolio portfolioById = inventoryService.getPortfolio(portfolio.getId());
        assertTrue(portfolioById.getCode().startsWith(portfolio.getCode()));
        assertEquals(portfolioById.getName(), portfolio.getName());
        assertEquals(portfolioById.getCustomer(), portfolio.getCustomer());
        assertEquals(portfolioById.getDescription(), portfolio.getDescription());
        assertNull(portfolioById.getParent());

    }

    @Test
    public void portfolioShouldBeUpdated() {
        InventoryServiceInterface inventoryService = new InventoryService();
        Portfolio portfolio = inventoryService.createPortfolio("PF1", "GrassHost", "customer jakis", "Opis Opisik", null);
        portfolio.setCode("PFFF");
        portfolio.setName("NOWY GrasshosT");
        portfolio = inventoryService.updatePortfolio(portfolio);
        Portfolio portfolioById = inventoryService.getPortfolio(portfolio.getId());
        assertTrue(portfolioById.getCode().startsWith("PFFF"));
        assertEquals(portfolioById.getName(), "NOWY GrasshosT");
        assertEquals(portfolioById.getCustomer(), portfolio.getCustomer());
        assertEquals(portfolioById.getDescription(), portfolio.getDescription());
        assertNull(portfolioById.getParent());
    }
    @Test(expected=InvalidParentComponentException.class)
    public void portfolioWithParentProgramShouldBeForbidenToUpdate() throws InvalidParentComponentException {
        InventoryServiceInterface inventoryService = new InventoryService();
        Portfolio portfolio = inventoryService.createPortfolio("PF1", "GrassHost", "customer jakis", "Opis Opisik", null);
        portfolio.setCode("PFFF");
        portfolio.setName("NOWY GrasshosT");
        Program program = inventoryService.createProgram("PF2", "Karczemka", "customer jakis", "Opis Opisik", null);
        portfolio.setParent(program);
    }
    @Test
    public void portfolioShouldBeDeleted(){
        InventoryServiceInterface inventoryService = new InventoryService();
        Portfolio portfolio = inventoryService.createPortfolio("PF1", "GrassHost", "customer jakis", "Opis Opisik", null);
        Portfolio portfolioById = inventoryService.getPortfolio(portfolio.getId());
        inventoryService.deletePortfolio(portfolio);
        Portfolio portfolioByIdDeleted = inventoryService.getPortfolio(portfolio.getId());
        assertNotNull(portfolioById);
        assertNull(portfolioByIdDeleted);
    }
    @Test
    public void aNewPortfolioWithSubportfolioShouldBeCreated() {
        InventoryServiceInterface inventoryService = new InventoryService();
        Portfolio portfolioParent = inventoryService.createPortfolio("PF1", "GrassHost", "customer jakis", "Opis Opisik", null);
        Portfolio portfolioChild = inventoryService.createPortfolio("PF2", "Karczemka", "customer jakis", "Opis Opisik", portfolioParent);
        portfolioParent = inventoryService.getPortfolio(portfolioParent.getId());

        assertNotNull(portfolioChild.getParent());
        assertEquals(portfolioChild.getParent().getId(), portfolioParent.getId());

        assertEquals(portfolioParent.getChildren().size(), 1);
    }

    //PROGRAM

    @Test
    public void aNewProgramShouldBeCreated() throws InvalidParentComponentException {
        InventoryServiceInterface inventoryService = new InventoryService();
        Portfolio portfolioTop = inventoryService.createPortfolio("PT", "Top", "customer jakis", "Opis Opisik", null);
        Program program = inventoryService.createProgram("PF1", "GrassHost", "customer jakis", "Opis Opisik", portfolioTop);
        assertEquals("PF1", program.getCode());
        assertEquals("GrassHost", program.getName());
        assertEquals("Opis Opisik", program.getDescription());
        assertNotEquals(program.getId(), 0);
        assertEquals(program.getParent().getId(), portfolioTop.getId());
    }
    @Test(expected=InvalidParentComponentException.class)
    public void aNewProjectWithSubprogramShouldBeForbidenToCreate() throws InvalidParentComponentException {
        InventoryServiceInterface inventoryService = new InventoryService();
        Portfolio portfolioTop = inventoryService.createPortfolio("PT", "Top", "customer jakis", "Opis Opisik", null);
        Project projectParent = inventoryService.createProject("PF1", "GrassHost", "customer jakis", "Opis Opisik", portfolioTop);
        Program programChild = inventoryService.createProgram("PF2", "Karczemka", "customer jakis", "Opis Opisik", projectParent);
    }
    @Test
    public void programShouldBeTakenById() throws InvalidParentComponentException {
        InventoryServiceInterface inventoryService = new InventoryService();
        Portfolio portfolioTop = inventoryService.createPortfolio("PT", "Top", "customer jakis", "Opis Opisik", null);
        Program program = inventoryService.createProgram("PF1", "GrassHost", "customer jakis", "Opis Opisik", portfolioTop);
        Program programById = inventoryService.getProgram(program.getId());
        assertTrue(programById.getCode().startsWith(program.getCode()));
        assertEquals(programById.getName(), program.getName());
        assertEquals(programById.getCustomer(), program.getCustomer());
        assertEquals(programById.getDescription(), program.getDescription());
        assertEquals(programById.getParent().getId(), portfolioTop.getId());

    }
    @Test
    public void programShouldBeUpdated() throws InvalidParentComponentException {
        InventoryServiceInterface inventoryService = new InventoryService();
        Portfolio portfolioTop = inventoryService.createPortfolio("PT", "Top", "customer jakis", "Opis Opisik", null);
        Program program = inventoryService.createProgram("PF1", "GrassHost", "customer jakis", "Opis Opisik", portfolioTop);
        program.setCode("PFFF");
        program.setName("NOWY GrasshosT");
        program = inventoryService.updateProgram(program);
        Program programById = inventoryService.getProgram(program.getId());
        assertTrue(programById.getCode().startsWith("PFFF"));
        assertEquals(programById.getName(), "NOWY GrasshosT");
        assertEquals(programById.getCustomer(), program.getCustomer());
        assertEquals(programById.getDescription(), program.getDescription());
        assertEquals(programById.getParent().getId(), portfolioTop.getId());
    }
    @Test(expected=InvalidParentComponentException.class)
    public void programWithParentProjectShouldBeForbidenToUpdate() throws InvalidParentComponentException {
        InventoryServiceInterface inventoryService = new InventoryService();
        Portfolio portfolioTop = inventoryService.createPortfolio("PT", "Top", "customer jakis", "Opis Opisik", null);
        Program program = inventoryService.createProgram("PF1", "GrassHost", "customer jakis", "Opis Opisik", portfolioTop);
        program.setCode("PFFF");
        program.setName("NOWY GrasshosT");
        Project project = inventoryService.createProject("PF2", "Karczemka", "customer jakis", "Opis Opisik", portfolioTop);
        program.setParent(project);
    }
    @Test
    public void programShouldBeDeleted() throws InvalidParentComponentException {
        InventoryServiceInterface inventoryService = new InventoryService();
        Portfolio portfolioTop = inventoryService.createPortfolio("PT", "Top", "customer jakis", "Opis Opisik", null);
        Program program = inventoryService.createProgram("PF1", "GrassHost", "customer jakis", "Opis Opisik", portfolioTop);
        Program programById = inventoryService.getProgram(program.getId());
        inventoryService.deleteProgram(program);
        Program programByIdDeleted = inventoryService.getProgram(program.getId());
        assertNotNull(programById);
        assertNull(programByIdDeleted);
    }
    @Test
    public void aNewProgramWithSubprogramShouldBeCreated() throws InvalidParentComponentException {
        InventoryServiceInterface inventoryService = new InventoryService();
        Portfolio portfolioTop = inventoryService.createPortfolio("PT", "Top", "customer jakis", "Opis Opisik", null);
        Program programParent = inventoryService.createProgram("PF1", "GrassHost", "customer jakis", "Opis Opisik", portfolioTop);
        Program programChild = inventoryService.createProgram("PF2", "Karczemka", "customer jakis", "Opis Opisik", programParent);
        programParent = inventoryService.getProgram(programParent.getId());

        assertNotNull(programChild.getParent());
        assertEquals(programChild.getParent().getId(), programParent.getId());

        assertEquals(programParent.getChildren().size(), 1);
    }
    @Test
    public void aNewPortfolioWithSubprogramShouldBeCreated() throws InvalidParentComponentException {
        InventoryServiceInterface inventoryService = new InventoryService();
        Portfolio portfolioParent = inventoryService.createPortfolio("PF1", "GrassHost", "customer jakis", "Opis Opisik", null);
        Program programChild = inventoryService.createProgram("PF2", "Karczemka", "customer jakis", "Opis Opisik", portfolioParent);
        portfolioParent = inventoryService.getPortfolio(portfolioParent.getId());

        assertNotNull(programChild.getParent());
        assertEquals(programChild.getParent().getId(), portfolioParent.getId());

        assertEquals(portfolioParent.getChildren().size(),1);
    }

    //PROJECT

    @Test
    public void aNewProjectShouldBeCreated() throws InvalidParentComponentException {
        InventoryServiceInterface inventoryService = new InventoryService();
        Portfolio portfolioTop = inventoryService.createPortfolio("PT", "Top", "customer jakis", "Opis Opisik", null);
        Project project = inventoryService.createProject("PF1", "GrassHost", "customer jakis", "Opis Opisik", portfolioTop);
        assertEquals("PF1", project.getCode());
        assertEquals("GrassHost", project.getName());
        assertEquals("Opis Opisik", project.getDescription());
        assertEquals(project.getParent().getId(), portfolioTop.getId());
        assertNotEquals(project.getId(), 0);
    }
    @Test(expected=InvalidParentComponentException.class)
    public void aNewProjectWithSubprojectShouldBeForbidenToCreate() throws InvalidParentComponentException {
        InventoryServiceInterface inventoryService = new InventoryService();
        Portfolio portfolioTop = inventoryService.createPortfolio("PT", "Top", "customer jakis", "Opis Opisik", null);
        Project projectParent = inventoryService.createProject("PF1", "GrassHost", "customer jakis", "Opis Opisik", portfolioTop);
        Project projectChild = inventoryService.createProject("PF2", "Karczemka", "customer jakis", "Opis Opisik", projectParent);
    }
    @Test(expected=InvalidParentComponentException.class)
    public void aNewProjectWithoutParentShouldBeForbidenToCreate() throws InvalidParentComponentException {
        InventoryServiceInterface inventoryService = new InventoryService();
        Project projectChild = inventoryService.createProject("PF2", "Karczemka", "customer jakis", "Opis Opisik", null);
    }
    @Test
    public void projectShouldBeTakenById() throws InvalidParentComponentException {
        InventoryServiceInterface inventoryService = new InventoryService();
        Portfolio portfolioTop = inventoryService.createPortfolio("PT", "Top", "customer jakis", "Opis Opisik", null);
        Project project = inventoryService.createProject("PF1", "GrassHost", "customer jakis", "Opis Opisik", portfolioTop);
        Project projectById = inventoryService.getProject(project.getId());
        assertTrue(projectById.getCode().startsWith(project.getCode()));
        assertEquals(projectById.getName(), project.getName());
        assertEquals(projectById.getCustomer(), project.getCustomer());
        assertEquals(projectById.getDescription(), project.getDescription());
        assertEquals(projectById.getParent().getId(),portfolioTop.getId());

    }
    @Test
    public void projectShouldBeUpdated() throws InvalidParentComponentException {
        InventoryServiceInterface inventoryService = new InventoryService();
        Portfolio portfolioTop = inventoryService.createPortfolio("PT", "Top", "customer jakis", "Opis Opisik", null);
        Project project = inventoryService.createProject("PF1", "GrassHost", "customer jakis", "Opis Opisik", portfolioTop);
        project.setCode("PFFF");
        project.setName("NOWY GrasshosT");
        project = inventoryService.updateProject(project);
        Project projectById = inventoryService.getProject(project.getId());
        assertTrue(projectById.getCode().startsWith("PFFF"));
        assertEquals(projectById.getName(), "NOWY GrasshosT");
        assertEquals(projectById.getCustomer(), project.getCustomer());
        assertEquals(projectById.getDescription(), project.getDescription());
        assertEquals(projectById.getParent().getId(), portfolioTop.getId());
    }
    @Test(expected=InvalidParentComponentException.class)
    public void projectWithParentProjectShouldBeForbidenToUpdate() throws InvalidParentComponentException {
        InventoryServiceInterface inventoryService = new InventoryService();
        Portfolio portfolioTop = inventoryService.createPortfolio("PT", "Top", "customer jakis", "Opis Opisik", null);
        Project project = inventoryService.createProject("PF1", "GrassHost", "customer jakis", "Opis Opisik", portfolioTop);
        project.setCode("PFFF");
        project.setName("NOWY GrasshosT");
        Project project2 = inventoryService.createProject("PF2", "Karczemka", "customer jakis", "Opis Opisik", portfolioTop);
        project.setParent(project2);
    }
    @Test
    public void projectShouldBeDeleted() throws InvalidParentComponentException {
        InventoryServiceInterface inventoryService = new InventoryService();
        Portfolio portfolioTop = inventoryService.createPortfolio("PT", "Top", "customer jakis", "Opis Opisik", null);
        Project project = inventoryService.createProject("PF1", "GrassHost", "customer jakis", "Opis Opisik", portfolioTop);
        Project projectById = inventoryService.getProject(project.getId());
        inventoryService.deleteProject(project);
        Project projectByIdDeleted = inventoryService.getProject(project.getId());
        assertNotNull(projectById);
        assertNull(projectByIdDeleted);
    }
    @Test
    public void aNewProgramWithSubprojectShouldBeCreated() throws InvalidParentComponentException {
        InventoryServiceInterface inventoryService = new InventoryService();
        Portfolio portfolioTop = inventoryService.createPortfolio("PT", "Top", "customer jakis", "Opis Opisik", null);
        Program programParent = inventoryService.createProgram("PF1", "GrassHost", "customer jakis", "Opis Opisik", portfolioTop);
        Project projectChild = inventoryService.createProject("PF2", "Karczemka", "customer jakis", "Opis Opisik", programParent);
        programParent = inventoryService.getProgram(programParent.getId());

        assertNotNull(projectChild.getParent());
        assertEquals(projectChild.getParent().getId(), programParent.getId());

        assertEquals(programParent.getChildren().size(), 1);
    }
    @Test
    public void aNewPortfolioWithSubprojectShouldBeCreated() throws InvalidParentComponentException {
        InventoryServiceInterface inventoryService = new InventoryService();
        Portfolio portfolioParent = inventoryService.createPortfolio("PF1", "GrassHost", "customer jakis", "Opis Opisik", null);
        Project projectChild = inventoryService.createProject("PF2", "Karczemka", "customer jakis", "Opis Opisik", portfolioParent);
        portfolioParent = inventoryService.getPortfolio(portfolioParent.getId());

        assertNotNull(projectChild.getParent());
        assertEquals(projectChild.getParent().getId(), portfolioParent.getId());

        assertEquals(portfolioParent.getChildren().size(),1);
    }

    //OPERATION

    @Test
    public void aNewOperationShouldBeCreated() throws InvalidParentComponentException {
        InventoryServiceInterface inventoryService = new InventoryService();
        Portfolio portfolioTop = inventoryService.createPortfolio("PT", "Top", "customer jakis", "Opis Opisik", null);
        Program programTop = inventoryService.createProgram("PrT", "TopProgram", "customer jakis", "Opis Opisik", portfolioTop);
        Operation operation = inventoryService.createOperation("PF1", "GrassHost", "customer jakis", "Opis Opisik", programTop, RecursionType.D);
        assertEquals("PF1", operation.getCode());
        assertEquals("GrassHost", operation.getName());
        assertEquals("Opis Opisik", operation.getDescription());
        assertEquals(operation.getParent().getId(), programTop.getId());
        assertNotEquals(operation.getId(), 0);
    }
    @Test(expected=InvalidParentComponentException.class)
    public void aNewOperationWithSuboperationShouldBeForbidenToCreate() throws InvalidParentComponentException {
        InventoryServiceInterface inventoryService = new InventoryService();
        Portfolio portfolioTop = inventoryService.createPortfolio("PT", "Top", "customer jakis", "Opis Opisik", null);
        Operation operationParent = inventoryService.createOperation("PF1", "GrassHost", "customer jakis", "Opis Opisik", portfolioTop, RecursionType.M);
        Operation operationChild = inventoryService.createOperation("PF2", "Karczemka", "customer jakis", "Opis Opisik", operationParent, RecursionType.M);
    }
    @Test(expected=InvalidParentComponentException.class)
    public void aNewOperationWithoutParentShouldBeForbidenToCreate() throws InvalidParentComponentException {
        InventoryServiceInterface inventoryService = new InventoryService();
        Operation operationChild = inventoryService.createOperation("PF2", "Karczemka", "customer jakis", "Opis Opisik", null, RecursionType.M);
    }
    @Test
    public void operationShouldBeTakenById() throws InvalidParentComponentException {
        InventoryServiceInterface inventoryService = new InventoryService();
        Portfolio portfolioTop = inventoryService.createPortfolio("PT", "Top", "customer jakis", "Opis Opisik", null);
        Program programTop = inventoryService.createProgram("PrT", "TopProgram", "customer jakis", "Opis Opisik", portfolioTop);
        Operation operation = inventoryService.createOperation("PF1", "GrassHost", "customer jakis", "Opis Opisik", programTop, RecursionType.M);
        Operation operationById = inventoryService.getOperation(operation.getId());
        assertEquals(operationById.getCode(), operation.getCode());
        assertEquals(operationById.getName(), operation.getName());
        assertEquals(operationById.getCustomer(), operation.getCustomer());
        assertEquals(operationById.getDescription(), operation.getDescription());
        assertEquals(operationById.getParent().getId(),programTop.getId());

    }
    @Test
    public void operationShouldBeUpdated() throws InvalidParentComponentException {
        InventoryServiceInterface inventoryService = new InventoryService();
        Portfolio portfolioTop = inventoryService.createPortfolio("PT", "Top", "customer jakis", "Opis Opisik", null);
        Program programTop = inventoryService.createProgram("PrT", "TopProgram", "customer jakis", "Opis Opisik", portfolioTop);
        Operation operation = inventoryService.createOperation("PF1", "GrassHost", "customer jakis", "Opis Opisik", programTop, RecursionType.M);
        operation.setCode("PFFF");
        operation.setName("NOWY GrasshosT");
        operation = inventoryService.updateOperation(operation);
        Operation operationById = inventoryService.getOperation(operation.getId());
        assertTrue(operationById.getCode().startsWith("PFFF"));
        assertEquals(operationById.getName(), "NOWY GrasshosT");
        assertEquals(operationById.getCustomer(), operation.getCustomer());
        assertEquals(operationById.getDescription(), operation.getDescription());
        assertEquals(operationById.getParent().getId(), programTop.getId());
    }
    @Test(expected=InvalidParentComponentException.class)
    public void operationWithParentOperationShouldBeForbidenToUpdate() throws InvalidParentComponentException {
        InventoryServiceInterface inventoryService = new InventoryService();
        Portfolio portfolioTop = inventoryService.createPortfolio("PT", "Top", "customer jakis", "Opis Opisik", null);
        Operation operation = inventoryService.createOperation("PF1", "GrassHost", "customer jakis", "Opis Opisik", portfolioTop, RecursionType.M);
        operation.setCode("PFFF");
        operation.setName("NOWY GrasshosT");
        Operation operation2 = inventoryService.createOperation("PF2", "Karczemka", "customer jakis", "Opis Opisik", portfolioTop, RecursionType.M);
        operation.setParent(operation2);
    }
    @Test
    public void operationShouldBeDeleted() throws InvalidParentComponentException {
        InventoryServiceInterface inventoryService = new InventoryService();
        Portfolio portfolioTop = inventoryService.createPortfolio("PT", "Top", "customer jakis", "Opis Opisik", null);
        Program programTop = inventoryService.createProgram("PrT", "TopProgram", "customer jakis", "Opis Opisik", portfolioTop);
        Operation operation = inventoryService.createOperation("PF1", "GrassHost", "customer jakis", "Opis Opisik", programTop, RecursionType.M);
        Operation operationById = inventoryService.getOperation(operation.getId());
        inventoryService.deleteOperation(operation);
        Operation operationByIdDeleted = inventoryService.getOperation(operation.getId());
        assertNotNull(operationById);
        assertNull(operationByIdDeleted);
    }
    @Test
    public void aNewProgramWithSuboperationShouldBeCreated() throws InvalidParentComponentException {
        InventoryServiceInterface inventoryService = new InventoryService();
        Portfolio portfolioTop = inventoryService.createPortfolio("PT", "Top", "customer jakis", "Opis Opisik", null);
        Program programParent = inventoryService.createProgram("PF1", "GrassHost", "customer jakis", "Opis Opisik", portfolioTop);
        Operation operationChild = inventoryService.createOperation("PF2", "Karczemka", "customer jakis", "Opis Opisik", programParent, RecursionType.M);
        programParent = inventoryService.getProgram(programParent.getId());

        assertNotNull(operationChild.getParent());
        assertEquals(operationChild.getParent().getId(), programParent.getId());

        assertEquals(programParent.getChildren().size(), 1);
    }
    @Test(expected=InvalidParentComponentException.class)
    public void aNewPortfolioWithSuboperationShouldBeForbidenToCreate() throws InvalidParentComponentException {
        InventoryServiceInterface inventoryService = new InventoryService();
        Portfolio portfolioTop = inventoryService.createPortfolio("PF1", "GrassHost", "customer jakis", "Opis Opisik", null);
        Operation operationChild = inventoryService.createOperation("PF2", "Karczemka", "customer jakis", "Opis Opisik", portfolioTop, RecursionType.M);
    }
    @Test(expected=InvalidParentComponentException.class)
    public void aNewPortfolioWithSuboperationShouldBeForbidenToUpdate() throws InvalidParentComponentException {
        InventoryServiceInterface inventoryService = new InventoryService();
        Portfolio portfolioTop = inventoryService.createPortfolio("PT", "Top", "customer jakis", "Opis Opisik", null);
        Program programParent = inventoryService.createProgram("PF1", "GrassHost", "customer jakis", "Opis Opisik", portfolioTop);
        Operation operationChild = inventoryService.createOperation("PF2", "Karczemka", "customer jakis", "Opis Opisik", programParent, RecursionType.M);
        operationChild.setParent(portfolioTop);
    }
    //FINAL TEST
    @Test
    public void aNewHierarchyTreeLikeInDocumentationIsCreated() throws InvalidParentComponentException {
        InventoryServiceInterface inventoryService = new InventoryService();
        Portfolio portfolioTop = inventoryService.createPortfolio("PT", "Top", "customer jakis", "Opis Opisik", null);
        Project portfolioProject = inventoryService.createProject("PF1", "GrassHost", "customer jakis", "Opis Opisik", portfolioTop);

        //subportfolio
        Portfolio subPortfolio = inventoryService.createPortfolio("SPT", "sub", "customer jakis", "Opis Opisik", portfolioTop);
        Program subPortfolioProgram = inventoryService.createProgram("PF1", "GrassHost", "customer jakis", "Opis Opisik", subPortfolio);
        Project subPortfolioProgramProject = inventoryService.createProject("PF1", "GrassHost", "customer jakis", "Opis Opisik", subPortfolioProgram);
        Project subPortfolioProject = inventoryService.createProject("PF1", "GrassHost", "customer jakis", "Opis Opisik", subPortfolio);

        //program
        Program portfolioProgram = inventoryService.createProgram("PF1", "GrassHost", "customer jakis", "Opis Opisik", portfolioTop);
        Operation portfolioProgramOperation = inventoryService.createOperation("PF2", "Karczemka", "customer jakis", "Opis Opisik", portfolioProgram, RecursionType.M);
        Project portfolioProgramProject = inventoryService.createProject("PF1", "GrassHost", "customer jakis", "Opis Opisik", portfolioProgram);

        //subprogram
        Program portfolioProgramSubprogram = inventoryService.createProgram("PF1", "GrassHost", "customer jakis", "Opis Opisik", portfolioProgram);
        Project portfolioProgramSubprogramProject = inventoryService.createProject("PF1", "GrassHost", "customer jakis", "Opis Opisik", portfolioProgramSubprogram);

    }

}