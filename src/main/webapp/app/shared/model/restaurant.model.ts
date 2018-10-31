export interface IRestaurant {
    id?: number;
    code?: string;
    isActive?: boolean;
    name?: string;
}

export class Restaurant implements IRestaurant {
    constructor(public id?: number, public code?: string, public isActive?: boolean, public name?: string) {
        this.isActive = this.isActive || false;
    }
}
