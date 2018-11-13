/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, protractor } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CouponComponentsPage, CouponDeleteDialog, CouponUpdatePage } from './coupon.page-object';

const expect = chai.expect;

describe('Coupon e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let couponUpdatePage: CouponUpdatePage;
    let couponComponentsPage: CouponComponentsPage;
    let couponDeleteDialog: CouponDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Coupons', async () => {
        await navBarPage.goToEntity('coupon');
        couponComponentsPage = new CouponComponentsPage();
        expect(await couponComponentsPage.getTitle()).to.eq('happybourBackEndApp.coupon.home.title');
    });

    it('should load create Coupon page', async () => {
        await couponComponentsPage.clickOnCreateButton();
        couponUpdatePage = new CouponUpdatePage();
        expect(await couponUpdatePage.getPageTitle()).to.eq('happybourBackEndApp.coupon.home.createOrEditLabel');
        await couponUpdatePage.cancel();
    });

    it('should create and save Coupons', async () => {
        const nbButtonsBeforeCreate = await couponComponentsPage.countDeleteButtons();

        await couponComponentsPage.clickOnCreateButton();
        await couponUpdatePage.setCodeInput('code');
        expect(await couponUpdatePage.getCodeInput()).to.eq('code');
        await couponUpdatePage.setDateCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
        expect(await couponUpdatePage.getDateCreatedInput()).to.contain('2001-01-01T02:30');
        await couponUpdatePage.setDateUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
        expect(await couponUpdatePage.getDateUpdatedInput()).to.contain('2001-01-01T02:30');
        await couponUpdatePage.setEndDateInput('2000-12-31');
        expect(await couponUpdatePage.getEndDateInput()).to.eq('2000-12-31');
        const selectedIsActive = couponUpdatePage.getIsActiveInput();
        if (await selectedIsActive.isSelected()) {
            await couponUpdatePage.getIsActiveInput().click();
            expect(await couponUpdatePage.getIsActiveInput().isSelected()).to.be.false;
        } else {
            await couponUpdatePage.getIsActiveInput().click();
            expect(await couponUpdatePage.getIsActiveInput().isSelected()).to.be.true;
        }
        await couponUpdatePage.setNoPerUserInput('5');
        expect(await couponUpdatePage.getNoPerUserInput()).to.eq('5');
        await couponUpdatePage.setPriceInput('5');
        expect(await couponUpdatePage.getPriceInput()).to.eq('5');
        await couponUpdatePage.setStartFromDateInput('2000-12-31');
        expect(await couponUpdatePage.getStartFromDateInput()).to.eq('2000-12-31');
        await couponUpdatePage.restaurantSelectLastOption();
        await couponUpdatePage.save();
        expect(await couponUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await couponComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Coupon', async () => {
        const nbButtonsBeforeDelete = await couponComponentsPage.countDeleteButtons();
        await couponComponentsPage.clickOnLastDeleteButton();

        couponDeleteDialog = new CouponDeleteDialog();
        expect(await couponDeleteDialog.getDialogTitle()).to.eq('happybourBackEndApp.coupon.delete.question');
        await couponDeleteDialog.clickOnConfirmButton();

        expect(await couponComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
