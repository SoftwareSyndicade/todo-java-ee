<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>TODO</title>
    <link href="styles/bootstrap.min.css" rel="stylesheet">
    <link href="styles/styles.css" rel="stylesheet">
    <link href="styles/index.css" rel="stylesheet">
</head>
<body>
    <div class="container-fluid max-supported-width p-3 h-100">
        <div class="stage">

        </div>
    </div>

    <script src="scripts/jquery-3.6.0.js"></script>
    <script src="scripts/bootstrap.min.js"></script>
    <script>

        var selectedFolder = 0
        $(document).ready(function(){
            loadFolders();
        })

        function loadFolders(){

            let options = {}
            options.url = '/todo-app/folder'
            options.method = 'GET'
            options.callbackMethod = loadFoldersSuccess

            sendRequest(options)
        }


        function loadFoldersSuccess(data){
            if(data !== "" || data !== undefined){
                let folders = JSON.parse(data)

                if(folders.length == 0){
                    // show no folders illustration
                    $('.stage').empty()
                    $('.stage').append($('template#no-folder-wrapper').html())
                }
                else{
                    $('.stage').empty()

                    let dashboard = $($('template#todo-folder-dashboard').html());
                    let dashboardStage = dashboard.find('.dashboard-stage')
                    let dashboardStageRow = dashboardStage.find('.row')

                    $(folders).each(function (index, folder){
                        let todoFolder = $($('template#todo-folder').html()).clone();

                        todoFolder.find('#folder-name').text(folder.name)
                        todoFolder.find('#folder-description').text(folder.description)
                        todoFolder.find('#action-todo-folder-delete button').click(function (){

                            let options = {}
                            options.url = '/todo-app/folder'
                            options.method = 'DELETE'
                            options.data =  'folder-id=' + folder.id,
                            options.callbackMethod = loadFolders

                            sendRequest(options)

                        })
                        todoFolder.find('#action-todo-folder-open button').click(function (){
                            selectedFolder = folder.id
                            loadTodos();
                        })
                        dashboardStageRow.append(todoFolder)
                    });

                    dashboardStage.append(dashboardStageRow)
                    $('.stage').append(dashboard)
                }
            }
        }

        function loadTodos(){

            let options = {}
            options.url = '/todo-app/todo'
            options.method = 'GET'
            options.data =  'folder-id=' + selectedFolder,
                options.callbackMethod = loadTodoSuccess

            sendRequest(options)
        }

        function loadTodoSuccess(data){
            if(data !== "" || data !== undefined){
                let todos = JSON.parse(data)

                if(todos.length == 0){
                    // show no folders illustration
                    $('.stage').empty()
                    $('.stage').append($('template#no-todo-wrapper').html())
                }
            }
        }

        function createFolder(){
            $('.stage').empty()
            $('.stage').append($('template#create-folder').html())
        }

        function saveFolder(){

            if(!isFolderValid())
                return false

            let options = {}
            options.url = '/todo-app/folder'
            options.method = 'POST'
            options.data = $('.create-folder').serialize()
            options.callbackMethod = saveFolderSuccess

            sendRequest(options)
        }


        function saveFolderSuccess(data){

            let response = JSON.parse(data)

            if(response){
                loadFolders()
            }
        }

        function isFolderValid(){
            // validate folder name
            let folderName = $('#txt-folder-name').val()

            if(folderName === "" || folderName === undefined)
            {
                alert("Please enter folder name.")
                return false
            }

            return true
        }

        function isTodoValid(){
            let todoName = $('#txt-todo-name').val()

            if(todoName === "" || todoName === undefined){
                alert("Please enter todo name.")
                return false
            }

            return true;
        }

        function sendRequest(options){
            /**
             * OPTIONS:
             * url
             * method
             * data
             * callbackMethod
             */

            if(options === undefined)
                return false

            $.ajax({
                url: options.url,
                // contentType: "application/json; charset=utf-8",
                method: options.method,
                data: options.data,
                success: options.callbackMethod
            })
        }

        function createTODO(){
            $('.stage').empty()
            $('.stage').append($('template#create-todo').html())
        }

        function saveTODO(){

            if(!isTodoValid())
                return false

            let options = {}
            options.url = '/todo-app/todo'
            options.method = 'POST'
            options.data = $('.create-todo').serialize() + '&folder-id=' + selectedFolder,
            options.callbackMethod = saveTodoSuccess()

            sendRequest(options)

        }

        function saveTodoSuccess(data){

        }

    </script>

    <template id="create-folder">
        <form class="padding2030 container-fluid create-folder" style="width: 400px">
            <div class="row">
                <div class="col-12 mb-2">
                    <h3>Create folder</h3>
                </div>
                <div class="col-12 form-group">
                    <label for="txt-folder-name">Folder name</label>
                    <input type="text" class="form-control" id="txt-folder-name" name="txt-folder-name" required>
                    <div class="invalid-feedback">
                        Please choose a username.
                    </div>
                </div>
                <div class="col-12 form-group">
                    <label for="txt-folder-description">Description</label>
                    <textarea type="text" class="form-control" id="txt-folder-description" name="txt-folder-description"></textarea>
                </div>
                <div class="col-12 text-end p-2">
                    <button class="btn" type="button" onclick="loadFolders()">Cancel</button>
                    <button class="btn btn-primary" type="button" onclick="saveFolder()">Save</button>
                </div>
            </div>
        </form>
    </template>
    <template id="no-folder-wrapper">
        <div class="text-center no-folder-wrapper">
            <img src="assets/empty.svg">
            <h3>No TODO folder found.</h3>
            <button class="btn btn-primary" onclick="createFolder()">Create folder</button>
        </div>
    </template>
    <template id="no-todo-wrapper">
        <div class="text-center no-todo-wrapper">
            <img src="assets/todos.svg">
            <h3>No TODO's found.</h3>
            <button class="btn btn-primary" onclick="createTODO()">Create TODO</button>
        </div>
    </template>
    <template id="todo-folder-dashboard">
        <div class="todo-folder-dashboard">
            <div class="text-end">
                <button class="btn btn-primary" onclick="createFolder()">Create folder</button>
                <hr/>
            </div>
            <div class="container-fluid dashboard-stage">
                <div class="row">

                </div>
            </div>
        </div>
    </template>
    <template id="todo-folder">
        <div class="col-3 p-2">
            <div class="todo-folder">
                <div class="todo-folder-status">
                    <label class="small">Updated: Today</label>
                </div>
                <div class="todo-options">
                    <img src="assets/cog-solid.svg">
                </div>
                <div class="folder-icon">
                    <img src="assets/folder-open-solid.svg">
                </div>
                <div class="text-center">
                    <h3 id="folder-name">Personal TODO's</h3>
                    <span class="small" id="folder-description">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</span>
                </div>
                <div class="container-fluid p-2">
                    <div class="row">
                        <div class="col-6" id="action-todo-folder-delete">
                            <button class="btn w-100">Delete</button>
                        </div>
                        <div class="col-6" id="action-todo-folder-open">
                            <button class="btn btn-primary w-100">View</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </template>
    <template id="todo-dashboard">
        <div class="todo-dashboard">
            <div class="text-end">
                <button class="btn btn-primary" onclick="createTODO()">Create TODO</button>
                <hr/>
            </div>
            <div class="container-fluid dashboard-stage">
                <div class="row" style="max-width: 800px">

                </div>
            </div>
        </div>
    </template>
    <template id="create-todo">
        <form class="padding2030 container-fluid create-todo" style="width: 400px">
            <div class="row">
                <div class="col-12 mb-2">
                    <h3>Create TODO</h3>
                </div>
                <div class="col-12 form-group">
                    <label for="txt-folder-name">TODO name</label>
                    <input type="text" class="form-control" id="txt-todo-name" name="txt-todo-name" required>
                </div>
                <div class="col-12 form-group">
                    <label for="txt-folder-description">Description</label>
                    <textarea type="text" class="form-control" id="txt-todo-description" name="txt-todo-description"></textarea>
                </div>
                <div class="col-12 text-end p-2">
                    <button class="btn" type="button" onclick="loadTodos()">Cancel</button>
                    <button class="btn btn-primary" type="button" onclick="saveTODO()">Save</button>
                </div>
            </div>
        </form>
    </template>
    <template id="todo">
        <div class="container-fluid todo">
            <div class="row">
                <div class="todo-checkbox">
                    <input type="checkbox">
                </div>
                <div class="col padding1015">
                    <h5 class="m-0" id="todo-name">Todo name</h5>
                    <label class="small" id="todo-description">Description here</label>
                </div>
            </div>
        </div>
    </template>
</body>
</html>