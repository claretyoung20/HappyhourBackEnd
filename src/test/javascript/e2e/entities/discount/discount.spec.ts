/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { DiscountComponentsPage, DiscountDeleteDialog, DiscountUpdatePage } from './discount.page-object';

const expect = chai.expect;

describe('Discount e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let discountUpdatePage: DiscountUpdatePage;
    let discountComponentsPage: DiscountComponentsPage;
    let discountDeleteDialog: DiscountDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Discounts', async () => {
        await navBarPage.goToEntity('discount');
        discountComponentsPage = new DiscountComponentsPage();
        expect(await discountComponentsPage.getTitle()).to.eq('happybourBackEndApp.discount.home.title');
    });

    it('should load create Discount page', async () => {
        await discountComponentsPage.clickOnCreateButton();
        discountUpdatePage = new DiscountUpdatePage();
        expect(await discountUpdatePage.getPageTitle()).to.eq('happybourBackEndApp.discount.home.createOrEditLabel');
        await discountUpdatePage.cancel();
    });

    it('should create and save Discounts', async () => {
        const nbButtonsBeforeCreate = await discountComponentsPage.countDeleteButtons();

        await discountComponentsPage.clickOnCreateButton();
        await discountUpdatePage.setAmountInput('5');
        expect(await discountUpdatePage.getAmountInput()).to.eq('5');
        await discountUpdatePage.setEndDateInput('2000-12-31');
        expect(await discountUpdatePage.getEndDateInput()).to.eq('2000-12-31');
        await discountUpdatePage.setStartDateInput('2000-12-31');
        expect(await discountUpdatePage.getStartDateInput()).to.eq('2000-12-31');
        await discountUpdatePage.setPercentageInput('5');
        expect(await discountUpdatePage.getPercentageInput()).to.eq('5');
        await discountUpdatePage.productSelectLastOption();
        await discountUpdatePage.save();
        expect(await discountUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await discountComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Discount', async () => {
        const nbButtonsBeforeDelete = await discountComponentsPage.countDeleteButtons();
        await discountComponentsPage.clickOnLastDeleteButton();

        discountDeleteDialog = new DiscountDeleteDialog();
        expect(await discountDeleteDialog.getDialogTitle()).to.eq('happybourBackEndApp.discount.delete.question');
        await discountDeleteDialog.clickOnConfirmButton();

        expect(await discountComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
