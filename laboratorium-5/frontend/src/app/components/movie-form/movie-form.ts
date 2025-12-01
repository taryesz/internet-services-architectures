import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { ApiService } from '../../services/api.service';

@Component({
  selector: 'app-movie-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './movie-form.html',
  styleUrl: './movie-form.css'
})
export class MovieFormComponent implements OnInit {

  genreId: string | null = null;
  movieId: string | null = null;

  title: string = '';
  rating: number = 0;
  pegi: boolean = false;

  constructor(
    private apiService: ApiService,
    private router: Router,
    private route: ActivatedRoute,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.genreId = this.route.snapshot.paramMap.get('genreId');
    this.movieId = this.route.snapshot.paramMap.get('movieId');

    if (this.movieId) {
      this.apiService.getMovie(this.movieId).subscribe(movie => {
        this.title = movie.title;
        this.rating = movie.rating;
        this.pegi = movie.pegi;
        this.cdr.detectChanges();
      });
    }
  }

  onSubmit(): void {
    if (!this.genreId) return;

    if (this.movieId) {
      this.apiService.updateMovie(this.movieId, this.title, this.rating, this.pegi, this.genreId)
        .subscribe(() => {
          this.router.navigate(['/genres', this.genreId]);
        });
    } else {
      this.apiService.createMovie(this.title, this.rating, this.pegi, this.genreId)
        .subscribe(() => {
          this.router.navigate(['/genres', this.genreId]);
        });
    }
  }

}
