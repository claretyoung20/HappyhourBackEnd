/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, protractor } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ProductComponentsPage, ProductDeleteDialog, ProductUpdatePage } from './product.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Product e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let productUpdatePage: ProductUpdatePage;
    let productComponentsPage: ProductComponentsPage;
    let productDeleteDialog: ProductDeleteDialog;
    const fileToUpload = '../../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Products', async () => {
        await navBarPage.goToEntity('product');
        productComponentsPage = new ProductComponentsPage();
        expect(await productComponentsPage.getTitle()).to.eq('happybourBackEndApp.product.home.title');
    });

    it('should load create Product page', async () => {
        await productComponentsPage.clickOnCreateButton();
        productUpdatePage = new ProductUpdatePage();
        expect(await productUpdatePage.getPageTitle()).to.eq('happybourBackEndApp.product.home.createOrEditLabel');
        await productUpdatePage.cancel();
    });

    it('should create and save Products', async () => {
        const nbButtonsBeforeCreate = await productComponentsPage.countDeleteButtons();

        await productComponentsPage.clickOnCreateButton();
        await productUpdatePage.setCreatedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
        expect(await productUpdatePage.getCreatedDateInput()).to.contain('2001-01-01T02:30');
        await productUpdatePage.setDescriptionInput('description');
        expect(await productUpdatePage.getDescriptionInput()).to.eq('description');
        await productUpdatePage.setNameInput('name');
        expect(await productUpdatePage.getNameInput()).to.eq('name');
        await productUpdatePage.setPriceInput('5');
        expect(await productUpdatePage.getPriceInput()).to.eq('5');
        await productUpdatePage.setUpdatedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
        expect(await productUpdatePage.getUpdatedDateInput()).to.contain('2001-01-01T02:30');
        const selectedIsAvailable = productUpdatePage.getIsAvailableInput();
        if (await selectedIsAvailable.isSelected()) {
            await productUpdatePage.getIsAvailableInput().click();
            expect(await productUpdatePage.getIsAvailableInput().isSelected()).to.be.false;
        } else {
            await productUpdatePage.getIsAvailableInput().click();
            expect(await productUpdatePage.getIsAvailableInput().isSelected()).to.be.true;
        }
        const selectedShowOnHomepage = productUpdatePage.getShowOnHomepageInput();
        if (await selectedShowOnHomepage.isSelected()) {
            await productUpdatePage.getShowOnHomepageInput().click();
            expect(await productUpdatePage.getShowOnHomepageInput().isSelected()).to.be.false;
        } else {
            await productUpdatePage.getShowOnHomepageInput().click();
            expect(await productUpdatePage.getShowOnHomepageInput().isSelected()).to.be.true;
        }
        await productUpdatePage.setProdct_imageInput(absolutePath);
        await productUpdatePage.restaurantSelectLastOption();
        await productUpdatePage.categorySelectLastOption();
        await productUpdatePage.save();
        expect(await productUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await productComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Product', async () => {
        const nbButtonsBeforeDelete = await productComponentsPage.countDeleteButtons();
        await productComponentsPage.clickOnLastDeleteButton();

        productDeleteDialog = new ProductDeleteDialog();
        expect(await productDeleteDialog.getDialogTitle()).to.eq('happybourBackEndApp.product.delete.question');
        await productDeleteDialog.clickOnConfirmButton();

        expect(await productComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
