import { IngredientResource } from '../resources/ingredient.resource';
import { ImageResource } from '../resources/image.resource';
import { CategoryResource } from '../resources/category.resource';
import { Request } from './request';

export class CreateRecipeRequest implements Request {
  private readonly _title: string;
  private readonly _description: string;
  private readonly _ingredients: IngredientResource[];
  private readonly _instructions: string;
  private readonly _image: ImageResource;
  private readonly _category: CategoryResource;
  private readonly _preparationTimeInMinutes?: number;
  private readonly _portions?: number;
  private readonly _calorificValue?: number;

  constructor(
    title: string,
    description: string,
    ingredients: IngredientResource[],
    instructions: string,
    image: ImageResource,
    category: CategoryResource,
    preparationTimeInMinutes?: number,
    portions?: number,
    calorificValue?: number,
  ) {
    this._title = title;
    this._description = description;
    this._ingredients = ingredients;
    this._instructions = instructions;
    this._image = image;
    this._category = category;
    this._preparationTimeInMinutes = preparationTimeInMinutes;
    this._portions = portions;
    this._calorificValue = calorificValue;
  }

  get title() {
    return this._title;
  }

  get description() {
    return this._description;
  }

  get ingredients() {
    return this._ingredients;
  }

  get instructions() {
    return this._instructions;
  }

  get image() {
    return this._image;
  }

  get category() {
    return this._category;
  }

  get preparationTimeInMinutes() {
    return this._preparationTimeInMinutes;
  }

  get portions() {
    return this._portions;
  }

  get calorificValue() {
    return this._calorificValue;
  }

  toRequestBody() {
    return {
      title: this._title,
      description: this._description,
      ingredients: this._ingredients.map((ingredient) => ({
        name: ingredient.name,
        quantity: {
          amount: ingredient.quantity.amount,
          unit: ingredient.quantity.unit,
        },
      })),
      instructions: this._instructions,
      image: {
        path: this._image.path,
      },
      category: {
        name: this._category.name,
      },
      preparationTimeInMinutes: this._preparationTimeInMinutes,
      portions: this._portions,
      calorificValue: this._calorificValue,
    };
  }
}
