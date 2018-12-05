/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { SaleOrderComponentsPage, SaleOrderDeleteDialog, SaleOrderUpdatePage } from './sale-order.page-object';

const expect = chai.expect;

describe('SaleOrder e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let saleOrderUpdatePage: SaleOrderUpdatePage;
    let saleOrderComponentsPage: SaleOrderComponentsPage;
    let saleOrderDeleteDialog: SaleOrderDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load SaleOrders', async () => {
        await navBarPage.goToEntity('sale-order');
        saleOrderComponentsPage = new SaleOrderComponentsPage();
        expect(await saleOrderComponentsPage.getTitle()).to.eq('happybourBackEndApp.saleOrder.home.title');
    });

    it('should load create SaleOrder page', async () => {
        await saleOrderComponentsPage.clickOnCreateButton();
        saleOrderUpdatePage = new SaleOrderUpdatePage();
        expect(await saleOrderUpdatePage.getPageTitle()).to.eq('happybourBackEndApp.saleOrder.home.createOrEditLabel');
        await saleOrderUpdatePage.cancel();
    });

    it('should create and save SaleOrders', async () => {
        const nbButtonsBeforeCreate = await saleOrderComponentsPage.countDeleteButtons();

        await saleOrderComponentsPage.clickOnCreateButton();
        await saleOrderUpdatePage.setBasePriceInput('5');
        expect(await saleOrderUpdatePage.getBasePriceInput()).to.eq('5');
        await saleOrderUpdatePage.setDateCreatedInput('2000-12-31');
        expect(await saleOrderUpdatePage.getDateCreatedInput()).to.eq('2000-12-31');
        await saleOrderUpdatePage.setDateUpdatedInput('2000-12-31');
        expect(await saleOrderUpdatePage.getDateUpdatedInput()).to.eq('2000-12-31');
        await saleOrderUpdatePage.productSelectLastOption();
        await saleOrderUpdatePage.happyOrderSelectLastOption();
        await saleOrderUpdatePage.save();
        expect(await saleOrderUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await saleOrderComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last SaleOrder', async () => {
        const nbButtonsBeforeDelete = await saleOrderComponentsPage.countDeleteButtons();
        await saleOrderComponentsPage.clickOnLastDeleteButton();

        saleOrderDeleteDialog = new SaleOrderDeleteDialog();
        expect(await saleOrderDeleteDialog.getDialogTitle()).to.eq('happybourBackEndApp.saleOrder.delete.question');
        await saleOrderDeleteDialog.clickOnConfirmButton();

        expect(await saleOrderComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
