import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { ApiService } from '../../services/api.service';
import { Movie } from '../../models/movie';

@Component({
  selector: 'app-movie-details',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './movie-details.html',
  styleUrl: './movie-details.css'
})

export class MovieDetailsComponent implements OnInit {

  genreId: string | null = null;
  movieId: string | null = null;
  movie: Movie | null = null;

  constructor(
    private apiService: ApiService,
    private route: ActivatedRoute,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {

    this.genreId = this.route.snapshot.paramMap.get('genreId');
    this.movieId = this.route.snapshot.paramMap.get('movieId');

    if (this.movieId) {
      this.fetchMovieData();
    }
  }

  fetchMovieData(): void {
    if (!this.movieId) return;

    this.apiService.getMovie(this.movieId).subscribe({
      next: (data) => {
        this.movie = data;
        this.cdr.detectChanges();
      },
      error: (err) => console.error('Blad pobierania filmu:', err)
    });
  }
}
