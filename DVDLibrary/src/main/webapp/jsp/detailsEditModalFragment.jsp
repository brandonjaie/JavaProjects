<div class="modal fade" id="detailsModal" tabindex="-1" role="dialog" aria-labelledby="detailsModalLabel" aria-hidden="true">
    <div class ="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">&times</span>
                    <span class="sr-only">Close</span>
                </button>
                <h4 clas="modal-title" id="detailsModalLabel">DVD Details</h4>
            </div>
            <div class="modal-body">
                <h3 id="dvd-id"></h3>
                <table class="table table-bordered">
                    <tr>
                        <th>Title:</th>
                        <td id="dvd-title"></td>
                    </tr>
                    <tr>
                        <th>Release Date:</th>
                        <td id="dvd-releaseDate"></td>
                    </tr>
                    <tr>
                        <th>MPAA Rating:</th>
                        <td id="dvd-mpaaRating"></td>
                    </tr>
                    <tr>
                        <th>Director:</th>
                        <td id="dvd-director"></td>
                    </tr>
                    <tr>
                        <th>Studio:</th>
                        <td id="dvd-studio"></td>
                    </tr>
                    <tr>
                        <th>Genre:</th>
                        <td id="dvd-genre"></td>
                    </tr>
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="editModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">&times</span>
                    <span class="sr-only">Close</span>
                </button>
                <h4 class="modal-title" id="editModalLabel">Edit DVD</h4>
            </div>
            <div class="modal-body">
                <h3 id="edit-header-dvd-id"></h3>
                <form class="form-horizontal" role="form">
                    <div class="form-group">
                        <label for="edit-title"class="col-md-4 control-label">Title:</label>
                        <div class="col-md-8">
                            <input type="text" class="form-control" id="edit-title" placeholder="Title:">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="edit-release-date"class="col-md-4 control-label">Release Date:</label>
                        <div class="col-md-8">
                            <input type="text" class="form-control" id="edit-release-date" placeholder="Release Date:">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="edit-mpaa-rating"class="col-md-4 control-label">MPAA Rating:</label>
                        <div class="col-md-8">
                            <input type="text" class="form-control" id="edit-mpaa-rating" placeholder="MPAA Rating:">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="edit-director" class="col-md-4 control-label">Director:</label>
                        <div class="col-md-8">
                            <input type="text" class="form-control" id="edit-director" placeholder="Director:">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="edit-studio"class="col-md-4 control-label">Studio:</label>
                        <div class="col-md-8">
                            <input type="text" class="form-control" id="edit-studio" placeholder="Studio:">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="edit-genre" class="col-md-4 control-label">Genre:</label>
                        <div class="col-md-8">
                            <select id="edit-genre" class="form-control">
                                <option value="" selected>Genre</option>
                                <option value="1">ACTION</option>
                                <option value="2">CLASSIC</option>
                                <option value="3">COMEDY</option>
                                <option value="4">DOCUMENTARY</option>
                                <option value="5">DRAMA</option>
                                <option value="6">ROMANCE</option>
                                <option value="7">THRILLER</option>
                                <option value="8">SCI-FI</option>
                            </select>
                        </div>
                    </div>
                    <div class ="form-group">
                        <div class="col-md-offset-4 col-md-8">
                            <input type="hidden" id="edit-dvd-id" />
                            <button type="submit" id="edit-button" class="btn btn-primary">Edit DVD</button>
                            <button type="button" class="btn btn-danger" data-dismiss="modal">Cancel</button>
                        </div>
                    </div>
                </form>
                <div id="validationErrorsEdit" style="color: red" />
            </div>     
        </div>
    </div>  
</div>