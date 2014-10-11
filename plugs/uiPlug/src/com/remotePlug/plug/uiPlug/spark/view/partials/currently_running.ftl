<div class="row">
    <div class="col-lg-12 text-center">
        <span style="float: left">
            <#list currentlyRunning as item>
                <a href="/item/${item.getId()}">
                    <button class="btn btn-success bt-lg">Now Playing <i class="icon icon-arrow-right"></i></button>
                </a>
            </#list>
        </span>
    </div>
</div>


