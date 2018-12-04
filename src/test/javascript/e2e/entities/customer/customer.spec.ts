/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, protractor } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CustomerComponentsPage, CustomerDeleteDialog, CustomerUpdatePage } from './customer.page-object';

const expect = chai.expect;

describe('Customer e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let customerUpdatePage: CustomerUpdatePage;
    let customerComponentsPage: CustomerComponentsPage;
    let customerDeleteDialog: CustomerDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Customers', async () => {
        await navBarPage.goToEntity('customer');
        customerComponentsPage = new CustomerComponentsPage();
        expect(await customerComponentsPage.getTitle()).to.eq('happybourBackEndApp.customer.home.title');
    });

    it('should load create Customer page', async () => {
        await customerComponentsPage.clickOnCreateButton();
        customerUpdatePage = new CustomerUpdatePage();
        expect(await customerUpdatePage.getPageTitle()).to.eq('happybourBackEndApp.customer.home.createOrEditLabel');
        await customerUpdatePage.cancel();
    });

    it('should create and save Customers', async () => {
        const nbButtonsBeforeCreate = await customerComponentsPage.countDeleteButtons();

        await customerComponentsPage.clickOnCreateButton();
        await customerUpdatePage.setAddressInput('address');
        expect(await customerUpdatePage.getAddressInput()).to.eq('address');
        await customerUpdatePage.setDateCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
        expect(await customerUpdatePage.getDateCreatedInput()).to.contain('2001-01-01T02:30');
        await customerUpdatePage.setDateUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
        expect(await customerUpdatePage.getDateUpdatedInput()).to.contain('2001-01-01T02:30');
        await customerUpdatePage.setDateOfBirthInput('2000-12-31');
        expect(await customerUpdatePage.getDateOfBirthInput()).to.eq('2000-12-31');
        await customerUpdatePage.setPhoneNumberInput('phoneNumber');
        expect(await customerUpdatePage.getPhoneNumberInput()).to.eq('phoneNumber');
        const selectedStatus = customerUpdatePage.getStatusInput();
        if (await selectedStatus.isSelected()) {
            await customerUpdatePage.getStatusInput().click();
            expect(await customerUpdatePage.getStatusInput().isSelected()).to.be.false;
        } else {
            await customerUpdatePage.getStatusInput().click();
            expect(await customerUpdatePage.getStatusInput().isSelected()).to.be.true;
        }
        await customerUpdatePage.restaurantSelectLastOption();
        await customerUpdatePage.userSelectLastOption();
        await customerUpdatePage.save();
        expect(await customerUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await customerComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Customer', async () => {
        const nbButtonsBeforeDelete = await customerComponentsPage.countDeleteButtons();
        await customerComponentsPage.clickOnLastDeleteButton();

        customerDeleteDialog = new CustomerDeleteDialog();
        expect(await customerDeleteDialog.getDialogTitle()).to.eq('happybourBackEndApp.customer.delete.question');
        await customerDeleteDialog.clickOnConfirmButton();

        expect(await customerComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
