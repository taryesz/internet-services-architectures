import {ChangeDetectorRef, Component} from '@angular/core';
import { FormsModule } from '@angular/forms';
import {ActivatedRoute, Router, RouterModule} from '@angular/router';
import { ApiService } from '../../services/api.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-genre-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './genre-form.html',
  styleUrl: './genre-form.css'
})
export class GenreFormComponent {

  id: string | null = null;
  name: string = '';
  actionIndex: number = 0;

  constructor(
    private apiService: ApiService,
    private router: Router,
    private route: ActivatedRoute,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {

    this.id = this.route.snapshot.paramMap.get('id');

    if (this.id) {
      this.apiService.getGenre(this.id).subscribe(data => {
        this.name = data.name;
        this.actionIndex = data.actionIndex;
        this.cdr.detectChanges();
      });
    }

  }

  onSubmit(): void {
    if (this.id) {
      this.apiService.updateGenre(this.id, this.name, this.actionIndex).subscribe(() => {
        this.router.navigate(['/genres']);
      });
    } else {
      this.apiService.createGenre(this.name, this.actionIndex).subscribe(() => {
        this.router.navigate(['/genres']);
      });
    }
  }

}
