import { Pipe, PipeTransform } from '@angular/core';
import { UnitResource } from '../../api/resources/unit.resource';

@Pipe({
  name: 'formatUnit',
})
export class FormatUnitPipe implements PipeTransform {
  transform(unit: UnitResource) {
    switch (unit) {
      case UnitResource.Gram:
        return 'g';
      case UnitResource.Kilogram:
        return 'kg';
      case UnitResource.Liter:
        return 'l';
      case UnitResource.Milliliter:
        return 'ml';
      case UnitResource.Piece:
        return 'pcs';
    }
  }
}
