/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { TableTypeComponentsPage, TableTypeDeleteDialog, TableTypeUpdatePage } from './table-type.page-object';

const expect = chai.expect;

describe('TableType e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let tableTypeUpdatePage: TableTypeUpdatePage;
    let tableTypeComponentsPage: TableTypeComponentsPage;
    let tableTypeDeleteDialog: TableTypeDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load TableTypes', async () => {
        await navBarPage.goToEntity('table-type');
        tableTypeComponentsPage = new TableTypeComponentsPage();
        expect(await tableTypeComponentsPage.getTitle()).to.eq('happybourBackEndApp.tableType.home.title');
    });

    it('should load create TableType page', async () => {
        await tableTypeComponentsPage.clickOnCreateButton();
        tableTypeUpdatePage = new TableTypeUpdatePage();
        expect(await tableTypeUpdatePage.getPageTitle()).to.eq('happybourBackEndApp.tableType.home.createOrEditLabel');
        await tableTypeUpdatePage.cancel();
    });

    it('should create and save TableTypes', async () => {
        const nbButtonsBeforeCreate = await tableTypeComponentsPage.countDeleteButtons();

        await tableTypeComponentsPage.clickOnCreateButton();
        await tableTypeUpdatePage.setCodeInput('code');
        expect(await tableTypeUpdatePage.getCodeInput()).to.eq('code');
        await tableTypeUpdatePage.setDescriptionInput('description');
        expect(await tableTypeUpdatePage.getDescriptionInput()).to.eq('description');
        await tableTypeUpdatePage.save();
        expect(await tableTypeUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await tableTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last TableType', async () => {
        const nbButtonsBeforeDelete = await tableTypeComponentsPage.countDeleteButtons();
        await tableTypeComponentsPage.clickOnLastDeleteButton();

        tableTypeDeleteDialog = new TableTypeDeleteDialog();
        expect(await tableTypeDeleteDialog.getDialogTitle()).to.eq('happybourBackEndApp.tableType.delete.question');
        await tableTypeDeleteDialog.clickOnConfirmButton();

        expect(await tableTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
