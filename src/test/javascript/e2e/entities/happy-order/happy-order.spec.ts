/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, protractor } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { HappyOrderComponentsPage, HappyOrderDeleteDialog, HappyOrderUpdatePage } from './happy-order.page-object';

const expect = chai.expect;

describe('HappyOrder e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let happyOrderUpdatePage: HappyOrderUpdatePage;
    let happyOrderComponentsPage: HappyOrderComponentsPage;
    let happyOrderDeleteDialog: HappyOrderDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load HappyOrders', async () => {
        await navBarPage.goToEntity('happy-order');
        happyOrderComponentsPage = new HappyOrderComponentsPage();
        expect(await happyOrderComponentsPage.getTitle()).to.eq('happybourBackEndApp.happyOrder.home.title');
    });

    it('should load create HappyOrder page', async () => {
        await happyOrderComponentsPage.clickOnCreateButton();
        happyOrderUpdatePage = new HappyOrderUpdatePage();
        expect(await happyOrderUpdatePage.getPageTitle()).to.eq('happybourBackEndApp.happyOrder.home.createOrEditLabel');
        await happyOrderUpdatePage.cancel();
    });

    it('should create and save HappyOrders', async () => {
        const nbButtonsBeforeCreate = await happyOrderComponentsPage.countDeleteButtons();

        await happyOrderComponentsPage.clickOnCreateButton();
        await happyOrderUpdatePage.setBaseTotalInput('5');
        expect(await happyOrderUpdatePage.getBaseTotalInput()).to.eq('5');
        await happyOrderUpdatePage.setDateCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
        expect(await happyOrderUpdatePage.getDateCreatedInput()).to.contain('2001-01-01T02:30');
        await happyOrderUpdatePage.setDateUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
        expect(await happyOrderUpdatePage.getDateUpdatedInput()).to.contain('2001-01-01T02:30');
        await happyOrderUpdatePage.setTotalPriceInput('5');
        expect(await happyOrderUpdatePage.getTotalPriceInput()).to.eq('5');
        await happyOrderUpdatePage.orderStatusSelectLastOption();
        await happyOrderUpdatePage.customerSelectLastOption();
        await happyOrderUpdatePage.couponSelectLastOption();
        await happyOrderUpdatePage.restaurantSelectLastOption();
        await happyOrderUpdatePage.staffSelectLastOption();
        await happyOrderUpdatePage.save();
        expect(await happyOrderUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await happyOrderComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last HappyOrder', async () => {
        const nbButtonsBeforeDelete = await happyOrderComponentsPage.countDeleteButtons();
        await happyOrderComponentsPage.clickOnLastDeleteButton();

        happyOrderDeleteDialog = new HappyOrderDeleteDialog();
        expect(await happyOrderDeleteDialog.getDialogTitle()).to.eq('happybourBackEndApp.happyOrder.delete.question');
        await happyOrderDeleteDialog.clickOnConfirmButton();

        expect(await happyOrderComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
