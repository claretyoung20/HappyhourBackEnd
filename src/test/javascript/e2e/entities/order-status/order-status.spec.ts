/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { OrderStatusComponentsPage, OrderStatusDeleteDialog, OrderStatusUpdatePage } from './order-status.page-object';

const expect = chai.expect;

describe('OrderStatus e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let orderStatusUpdatePage: OrderStatusUpdatePage;
    let orderStatusComponentsPage: OrderStatusComponentsPage;
    let orderStatusDeleteDialog: OrderStatusDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load OrderStatuses', async () => {
        await navBarPage.goToEntity('order-status');
        orderStatusComponentsPage = new OrderStatusComponentsPage();
        expect(await orderStatusComponentsPage.getTitle()).to.eq('happybourBackEndApp.orderStatus.home.title');
    });

    it('should load create OrderStatus page', async () => {
        await orderStatusComponentsPage.clickOnCreateButton();
        orderStatusUpdatePage = new OrderStatusUpdatePage();
        expect(await orderStatusUpdatePage.getPageTitle()).to.eq('happybourBackEndApp.orderStatus.home.createOrEditLabel');
        await orderStatusUpdatePage.cancel();
    });

    it('should create and save OrderStatuses', async () => {
        const nbButtonsBeforeCreate = await orderStatusComponentsPage.countDeleteButtons();

        await orderStatusComponentsPage.clickOnCreateButton();
        await orderStatusUpdatePage.setNameInput('name');
        expect(await orderStatusUpdatePage.getNameInput()).to.eq('name');
        await orderStatusUpdatePage.save();
        expect(await orderStatusUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await orderStatusComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last OrderStatus', async () => {
        const nbButtonsBeforeDelete = await orderStatusComponentsPage.countDeleteButtons();
        await orderStatusComponentsPage.clickOnLastDeleteButton();

        orderStatusDeleteDialog = new OrderStatusDeleteDialog();
        expect(await orderStatusDeleteDialog.getDialogTitle()).to.eq('happybourBackEndApp.orderStatus.delete.question');
        await orderStatusDeleteDialog.clickOnConfirmButton();

        expect(await orderStatusComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
